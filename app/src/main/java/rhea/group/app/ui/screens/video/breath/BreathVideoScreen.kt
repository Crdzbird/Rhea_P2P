package rhea.group.app.ui.screens.video.breath

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import rhea.group.app.R
import rhea.group.app.components.LockScreenOrientation
import rhea.group.app.components.frosted
import rhea.group.app.models.breathTypePosition
import rhea.group.app.models.convert
import rhea.group.app.timer.TimerViewState
import rhea.group.app.ui.screens.video.breath.components.BreathCountDownBox
import rhea.group.app.ui.screens.video.breath.components.BreathHeart
import rhea.group.app.ui.screens.video.breath.components.BreathVideoControl
import rhea.group.app.ui.screens.video.breath.viewModel.BreathVideoViewModel
import rhea.group.app.ui.screens.video.components.FinishWorkoutDialog
import rhea.group.app.ui.theme.Black
import rhea.group.app.ui.theme.Mirage
import rhea.group.app.ui.theme.Mirage_45
import rhea.group.app.utils.toSeconds
import rhea.group.app.wearable.WearableViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BreathVideoScreen(
    breathVideoViewModel: BreathVideoViewModel = hiltViewModel(),
    wearableViewModel: WearableViewModel = hiltViewModel()
) {
    val scope = CoroutineScope(Dispatchers.IO)
    wearableViewModel.init(startReceiver = true)
    wearableViewModel.checkWearable(scope)
    LockScreenOrientation(
        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,
        statusBarVisible = false,
        navigationBarVisible = false,
        systemBarVisible = false
    )
    val videoState = breathVideoViewModel.videoState.collectAsState().value
    val heartRate = wearableViewModel.heartRateState.collectAsState().value
    breathVideoViewModel.preventLock()
    var showFinishWorkoutDialog by remember { mutableStateOf(false) }
    val videoControllerVisibility = remember { mutableStateOf(true) }
    val isLoading by videoState.isLoading.collectAsState()

    val remainingTime = breathVideoViewModel.remainingTime.collectAsState().value
    val isRunning = breathVideoViewModel.isRunning.collectAsState().value
    val timerState = breathVideoViewModel.timerState.collectAsState().value

    if (timerState == TimerViewState.RESTART) {
        breathVideoViewModel.increment()
        breathVideoViewModel.checkTimeZero()
        breathVideoViewModel.startCountDownTimer()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = Black
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        videoControllerVisibility.value = !videoControllerVisibility.value
                    },
                factory = { context ->
                    StyledPlayerView(context).apply {
                        keepScreenOn = true
                        useController = false
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                        player = videoState.player
                    }
                }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .wrapContentSize(
                            unbounded = true
                        )
                        .align(
                            alignment = Alignment.TopStart
                        )
                        .padding(
                            top = 10.dp,
                            start = 10.dp
                        )
                        .background(
                            color = Mirage_45,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    BreathHeart(
                        heartRate = heartRate.toString()
                    )
                }
                Box(
                    modifier = Modifier
                        .wrapContentSize(
                            align = Alignment.TopCenter,
                            unbounded = true
                        )
                        .align(
                            alignment = Alignment.Center
                        )
                        .background(
                            color = Mirage_45,
                            shape = RoundedCornerShape(30.dp)
                        )
                ) {
                    if (videoControllerVisibility.value) {
                        BreathCountDownBox(
                            currentTimeInSeconds = breathVideoViewModel.fetchSeconds(
                                remainingTime.toSeconds().toInt()
                            ),
                            title = stringResource(
                                id = convert(
                                    breathTypePosition(
                                        breathVideoViewModel.position
                                    )
                                )
                            )
                        )
                    }
                    if (isLoading) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.size(55.dp))
                        }
                    }
                }
                if (videoControllerVisibility.value) {
                    BreathVideoControl(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(
                                alignment = Alignment.BottomStart
                            )
                            .padding(
                                start = 75.dp,
                                end = 75.dp,
                                bottom = 35.dp
                            ),
                        isPlaying = isRunning,
                        onPlayClick = {
                            if (timerState == TimerViewState.IN_PROGRESS) {
                                breathVideoViewModel.pauseCountDownTimer()
                                return@BreathVideoControl
                            }
                            if (timerState == TimerViewState.PAUSED) {
                                breathVideoViewModel.resumeCountDownTimer()
                                return@BreathVideoControl
                            }
                            breathVideoViewModel.play()
                        },
                        onCloseClick = {
                            breathVideoViewModel.pauseCountDownTimer()
                            showFinishWorkoutDialog = true
                        },
                    )
                }
            }
        }
        if (showFinishWorkoutDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .frosted(
                        color = Mirage,
                        alpha = 0.7f,
                        borderRadius = 20.dp,
                        shadowRadius = 30.dp,
                        offsetX = 0.dp,
                        offsetY = 0.dp
                    )
            )
            FinishWorkoutDialog(
                modifier = Modifier
                    .width(500.dp),
                title = stringResource(id = R.string.end_workout_question),
                description = stringResource(id = R.string.end_workout_description),
                dialogState = showFinishWorkoutDialog,
                onDialogPositiveButtonClicked = {
                                                breathVideoViewModel.navigateTo()
                },
                onDismissRequest = {
                    showFinishWorkoutDialog = false
                }
            )
        }
    }
}