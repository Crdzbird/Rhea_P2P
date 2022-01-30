package rhea.group.app.exoplayer

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ExoPlaybackException.TYPE_SOURCE
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LivePlayer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorHandler: () -> Unit,
) {
    val TAG = "LivePlayer"

    var currentUri = ""
    lateinit var _onPlayComplete: () -> Unit

    private val _isPlayTriggered = MutableStateFlow(false)
    val isPlayTriggered: StateFlow<Boolean> = _isPlayTriggered
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _isPlayingEnded = MutableStateFlow(false)
    val isPlayingEnded: StateFlow<Boolean> = _isPlayingEnded
    private val _playbackState = MutableStateFlow(-1)
    val playbackState: StateFlow<Int> = _playbackState
    private val _contentDuration = MutableStateFlow(-1)
    val contentDuration: StateFlow<Int> = _contentDuration
    private val _contentDurationFixed = MutableStateFlow(-1)
    val contentDurationFixed: StateFlow<Int> = _contentDurationFixed
    private val _contentPosition = MutableStateFlow(-1)
    val contentPosition: StateFlow<Int> = _contentPosition
    private val _bufferedPercentage = MutableStateFlow(-1)
    val bufferedPercentage: StateFlow<Int> = _bufferedPercentage
    private val _currentPosition = MutableStateFlow(-1)
    val currentPosition: StateFlow<Int> = _currentPosition

    private val formatBuilder = StringBuilder()
    private val formatter = Formatter(formatBuilder, Locale.getDefault())
    private val period = Timeline.Period()
    private val window = Timeline.Window()
    private var currentWindowOffset: Long = 0
    private val updateProgressAction = Runnable { updateProgress() }
    private var adGroupTimesMs = LongArray(0)
    private var playedAdGroups = BooleanArray(0)
    private val extraAdGroupTimesMs = LongArray(0)
    private val extraPlayedAdGroups = BooleanArray(0)
    private val MAX_UPDATE_INTERVAL_MS = 1000
    private val MIN_UPDATE_INTERVAL_MS = 200

    private val _player = ExoPlayer.Builder(context).build().apply {
        addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    _isPlaying.value = isPlaying
                    _isPlayTriggered.value = isPlaying
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    _playbackState.value = playbackState
                }

                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                }

                override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                    super.onTimelineChanged(timeline, reason)
                }

                override fun onTracksInfoChanged(tracksInfo: TracksInfo) {
                    super.onTracksInfoChanged(tracksInfo)
                }

                override fun onAudioSessionIdChanged(audioSessionId: Int) {
                    super.onAudioSessionIdChanged(audioSessionId)
                }

                override fun onIsLoadingChanged(isLoading: Boolean) {
                    super.onIsLoadingChanged(isLoading)
                    _isLoading.value = isLoading
                }

                override fun onPlayerError(error: PlaybackException) {
                    if (error is ExoPlaybackException && error.type == TYPE_SOURCE) {
                        errorHandler.invoke()
                        return
                    }
                    super.onPlayerError(error)
                    _isLoading.value = false
                    _isPlaying.value = false
                    _isPlayTriggered.value = false
                }
            }
        )
    }

    val player = _player

    fun pause() {
        _isPlayTriggered.value = false
        _player.pause()
    }

    fun restart() {
        _isPlayTriggered.value = true
        _player.seekTo(0)
        updateTimeline()
    }

    fun play() {
        _isPlayTriggered.value = true
        when (_playbackState.value) {
            ExoPlayer.STATE_ENDED -> {
                restart()
            }
            ExoPlayer.STATE_IDLE -> {
                init()
            }
            else -> {
                _player.play()
            }
        }
    }

    fun toggle() {
        if (_isPlayTriggered.value || _player.isPlaying) {
            pause()
        } else {
            play()
        }
    }

    fun repeat() {
        _player.repeatMode = Player.REPEAT_MODE_ONE
    }

    fun init(
        uri: String? = null,
        playWhenReady: Boolean = true,
        repeat: Boolean = false,
        onPlayComplete: (() -> Unit)? = null
    ) {
        if (uri != null) currentUri = uri
        if (onPlayComplete != null) _onPlayComplete = onPlayComplete
        val dataSourceFactory = CacheDataSourceFactory(context)
        _player.apply {
            this.playWhenReady = playWhenReady
            setMediaSource(dataSourceFactory.createMediaSource(currentUri))
            if (repeat) {
                repeat()
            }
            prepare()
        }
        updateTimeline()
    }


    private fun updateTimeline() {
        val player = _player
        var durationUs: Long = 0
        var adGroupCount = 0
        val timeline = player.currentTimeline
        if (!timeline.isEmpty) {
            val currentWindowIndex = player.currentMediaItemIndex
            for (i in currentWindowIndex..currentWindowIndex) {
                if (i == currentWindowIndex) {
                    currentWindowOffset = Util.usToMs(durationUs)
                }
                timeline.getWindow(i, window)

                for (j in window.firstPeriodIndex..window.lastPeriodIndex) {
                    timeline.getPeriod(j, period)
                    val removedGroups: Int = period.removedAdGroupCount
                    val totalGroups: Int = period.adGroupCount
                    for (adGroupIndex in removedGroups until totalGroups) {
                        var adGroupTimeInPeriodUs: Long = period.getAdGroupTimeUs(adGroupIndex)
                        if (adGroupTimeInPeriodUs == C.TIME_END_OF_SOURCE) {
                            if (period.durationUs == C.TIME_UNSET) {
                                // Don't show ad markers for postrolls in periods with unknown duration.
                                continue
                            }
                            adGroupTimeInPeriodUs = period.durationUs
                        }
                        val adGroupTimeInWindowUs: Long =
                            adGroupTimeInPeriodUs + period.getPositionInWindowUs()
                        if (adGroupTimeInWindowUs >= 0) {
                            if (adGroupCount == adGroupTimesMs.size) {
                                val newLength =
                                    if (adGroupTimesMs.isEmpty()) 1 else adGroupTimesMs.size * 2
                                adGroupTimesMs = adGroupTimesMs.copyOf(newLength)
                                playedAdGroups = playedAdGroups.copyOf(newLength)
                            }
                            adGroupTimesMs[adGroupCount] =
                                Util.usToMs(durationUs + adGroupTimeInWindowUs)
                            playedAdGroups[adGroupCount] = period.hasPlayedAdGroup(adGroupIndex)
                            adGroupCount++
                        }
                    }
                }
                durationUs += window.durationUs
            }
        }
        val durationMs = Util.usToMs(durationUs)
        val durationMsFormatted = Util.getStringForTime(formatBuilder, formatter, durationMs)
        updateProgress()
    }

    private fun updateProgress() {
        val player: Player? = _player
        var position: Long = 0
        var bufferedPosition: Long = 0
        var positionFormatted = ""
        player?.let {
            position = currentWindowOffset + it.contentPosition
            positionFormatted = Util.getStringForTime(formatBuilder, formatter, position)
            bufferedPosition = currentWindowOffset + it.contentBufferedPosition

            _contentDuration.value = TimeUnit.MILLISECONDS.toSeconds(position).toInt()
            _contentDurationFixed.value = TimeUnit.MILLISECONDS.toSeconds(it.duration).toInt()

            if (_contentDuration.value >= _contentDurationFixed.value) {
                _onPlayComplete()
            }
        }
        // Cancel any pending updates and schedule a new one if necessary.
        Handler(Looper.getMainLooper())
            .removeCallbacks(updateProgressAction)
        val playbackState = player?.playbackState
        if (player != null && player.isPlaying) {
            var mediaTimeDelayMs = MAX_UPDATE_INTERVAL_MS.toLong()

            // Limit delay to the start of the next full second to ensure position display is smooth.
            val mediaTimeUntilNextFullSecondMs = 1000 - position % 1000
            mediaTimeDelayMs = mediaTimeDelayMs.coerceAtMost(mediaTimeUntilNextFullSecondMs)

            // Calculate the delay until the next update in real time, taking playback speed into account.
            val playbackSpeed = player.playbackParameters.speed
            var delayMs =
                if (playbackSpeed > 0) (mediaTimeDelayMs / playbackSpeed).toLong() else MAX_UPDATE_INTERVAL_MS.toLong()

            // Constrain the delay to avoid too frequent / infrequent updates.
            delayMs = Util.constrainValue(
                delayMs,
                MIN_UPDATE_INTERVAL_MS.toLong(), MAX_UPDATE_INTERVAL_MS.toLong()
            )
            Handler(Looper.getMainLooper())
                .postDelayed(updateProgressAction, delayMs)
        } else if (playbackState != Player.STATE_ENDED && playbackState != Player.STATE_IDLE) {
            Handler(Looper.getMainLooper())
                .postDelayed(
                    updateProgressAction, MAX_UPDATE_INTERVAL_MS.toLong()
                )
        }
    }
}