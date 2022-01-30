package rhea.group.app.ui.screens.video.breath.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.os.PowerManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import rhea.group.app.exoplayer.LivePlayer
import rhea.group.app.exoplayer.LivePlayerInterface
import rhea.group.app.models.BreathworkOption
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.timer.TimerViewState
import rhea.group.app.utils.breathworkOptionParam
import rhea.group.app.utils.toSecond
import javax.inject.Inject

@HiltViewModel
class BreathVideoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val preferences: PrefsModeImpl,
    livePlayer: LivePlayer,
    livePlayerInterface: LivePlayerInterface,
    private val navigator: ComposeNavigator,
    application: Application
) : AndroidViewModel(application) {
    val sessionId = savedStateHandle.get<String>(Screen.BreathVideoScreen.navArguments[0].name) ?: ""
    var totalDuration = (savedStateHandle.get<Int>(Screen.BreathVideoScreen.navArguments[1].name)!! * 1000L * 60)
    val duration = savedStateHandle.get<Int>(Screen.BreathVideoScreen.navArguments[1].name)!!.toSecond()
    private val _videoState = MutableStateFlow(livePlayer)
    val videoState = _videoState.asStateFlow()
    var modelBreathworkOption: BreathworkOption? = null
    var timers: List<Int> = emptyList()
    var navController: NavController? = null

    private val context
        get() = getApplication<Application>()

    var finalDuration: Int = 0
    var position: Int = 0

    private var timer: CountDownTimer? = null

    private val _remainingTime = MutableStateFlow(0L)
    val remainingTime: StateFlow<Long> = _remainingTime
    private var _timerState = MutableStateFlow(TimerViewState.IDLE)
    val timerState: StateFlow<TimerViewState> = _timerState
    private var _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    fun checkTimeZero() {
        if (timers[position] != 0) return
        increment()
    }

    fun increment() {
        position += 1
        if (position >= timers.size) {
            position = 0
            return
        }
    }

    fun play() {
        _isRunning.value = true
        startCountDownTimer()
    }

    fun restart() {
        _videoState.value.restart()
    }

    init {
        livePlayer.player.addListener(livePlayerInterface)
        finalDuration = duration.toSecond()
        modelBreathworkOption = breathworkOptionParam
        timers = breathworkOptionParam.breathLimits
        _remainingTime.value = timers[position].toLong()
        createTimer(timers[position].toLong())
        postInit()
    }

    private fun postInit(playWhenReady: Boolean = true) {
        _videoState.value.init(
            modelBreathworkOption!!.backgroundVideoUrl,
            playWhenReady,
            true,
            onPlayComplete = {})
    }


    @SuppressLint("InvalidWakeLockTag")
    fun preventLock() {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            this@BreathVideoViewModel::javaClass.name
        )
        wakeLock.acquire(totalDuration * 2L)
        wakeLock.release()
    }

    fun startCountDownTimer() {
        if (position >= timers.size) position = 0
        createTimer(totalTime = timers[position].toLong())
        timer?.start()
        _timerState.value = TimerViewState.IN_PROGRESS
    }

    fun pauseCountDownTimer() {
        timer?.cancel()
        timer = null
        _isRunning.value = false
        _timerState.value = TimerViewState.PAUSED
    }

    fun resumeCountDownTimer() {
        _timerState.value = TimerViewState.IN_PROGRESS
        _isRunning.value = true
        createTimer(totalTime = timers[position].toLong())
        timer?.start()
    }

    private fun createTimer(totalTime: Long) {
        timer = object : CountDownTimer(((totalTime + 1) * 1000), 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished == (totalTime + 1) * 1000) {
                    totalDuration += 2000
                }
                totalDuration -= 1000
                _remainingTime.value = millisUntilFinished
            }

            override fun onFinish() {
                if (totalDuration <= 0) {
                    _timerState.value = TimerViewState.FINISHED
                    _isRunning.value = false
                    navController?.navigate("${Screen.SymptomsScreen.route}/$sessionId/${modelBreathworkOption?.breathworkType}/$finalDuration")
                } else {
                    _remainingTime.value = totalTime
                    if (position >= timers.size) {
                        position = 0
                    }
                    _timerState.value = TimerViewState.RESTART
                }
            }
        }
    }

    fun fetchSeconds(seconds: Int): Int {
        if (timers.isEmpty()) { return 0 }
        return if (seconds > timers[position]) {
            timers[position]
        } else {
            seconds
        }
    }

    fun navigateTo() = navigator.navigate(Screen.EndingWorkoutScreen.createRoute(sessionId))
}