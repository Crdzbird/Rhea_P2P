package rhea.group.app.ui.screens.video.session.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.PowerManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import rhea.group.app.exoplayer.LivePlayer
import rhea.group.app.exoplayer.LivePlayerInterface
import rhea.group.app.models.Exercise
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.utils.exercises
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: ComposeNavigator,
    private val livePlayer: LivePlayer,
    livePlayerInterface: LivePlayerInterface,
    application: Application
) : AndroidViewModel(application) {
    private val sessionId = savedStateHandle.get<String>(Screen.VideoScreen.navArguments[0].name) ?: ""
    val isPreview = savedStateHandle.get<Boolean>(Screen.VideoScreen.navArguments[1].name) ?: false

    private val _videoState = MutableStateFlow(livePlayer)
    val videoState = _videoState.asStateFlow()
    val isPlaying = MutableLiveData(livePlayer.player.isPlaying)
    private val context
        get() = getApplication<Application>()
//    exercises: List<Exercise>,
//    session: String,
//    isPreview: Boolean

    var totalDuration: Int = 0
    var position: Int = 0
    var exerciseCompleted: Boolean = false
    var modelExercises: MutableList<Exercise> = mutableListOf()

    init {
        livePlayer.player.addListener(livePlayerInterface)
        modelExercises.clear()
        modelExercises.addAll(exercises)
        totalDuration = 0
        exercises.map { e -> totalDuration += e.duration }
        postInit(isPreview = isPreview)
    }

    fun pause() {
        _videoState.value.pause()
    }

    fun play() {
        _videoState.value.play()
    }

    fun next() {
        if (position + 1 < modelExercises.size) {
            position++
            postInit()
        } else {
            exerciseCompleted = true
        }
    }

    fun previous() {
        if (position > 0) {
            position--
            postInit()
        }
    }

    fun toggle() {
        exerciseCompleted = false
        _videoState.value.toggle()
    }

    fun restart() {
        _videoState.value.restart()
    }

    fun repeat() {
        pause()
        restart()
        play()
    }

    fun postInit(playWhenReady: Boolean = true, isPreview: Boolean = false) {
        if (modelExercises.size <= position) return
        exerciseCompleted = false
        _videoState.value.init(modelExercises[position].videoUrl, playWhenReady, onPlayComplete = {
            if (!isPreview) next()
        })
    }

    val currentTotalDuration: Int
        get() {
            var duration = 0
            for (i in modelExercises.indices) {
                if (i >= position) {
                    duration += modelExercises[i].duration
                }
            }
            return duration
        }

    val currentExercise: Exercise?
        get() {
            if (modelExercises.size < 1 || modelExercises.size <= position) return null
            return modelExercises[position]
        }

    val nextExercise: Exercise?
        get() {
            if (isPlayingLast) return null
            return modelExercises[position + 1]
        }

    val isPlayingLast: Boolean
        get() {
            if (modelExercises.size < 1 || modelExercises.size <= position) return true
            return position == modelExercises.size - 1;
        }

    fun onDestroy() {
        livePlayer.player.release()
    }

    @SuppressLint("InvalidWakeLockTag")
    fun preventLock(duration: Long) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            this@VideoViewModel::javaClass.name
        )
        //wakeLock.acquire(20 * 60 * 1000L /*20 minutes*/)
        wakeLock.acquire(duration)
        wakeLock.release()
    }

    fun onExerciseCompleted() = navigator.navigate(Screen.SymptomsScreen.createRoute(sessionId, null, null))
    fun navigateTo() = navigator.navigate(Screen.EndingWorkoutScreen.createRoute(sessionId))

}