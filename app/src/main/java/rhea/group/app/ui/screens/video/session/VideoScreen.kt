package rhea.group.app.ui.screens.video.session

import android.content.pm.ActivityInfo
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import rhea.group.app.models.ExerciseCategoryType
import rhea.group.app.ui.screens.video.components.*
import rhea.group.app.ui.screens.video.session.viewModel.VideoViewModel
import rhea.group.app.ui.theme.Black
import rhea.group.app.ui.theme.Mirage
import rhea.group.app.ui.theme.Mirage_45
import rhea.group.app.wearable.WearableViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VideoScreen(
//    navController: NavHostController,
    videoViewModel: VideoViewModel = hiltViewModel(),
    wearableViewModel: WearableViewModel = hiltViewModel(),
) {
    LockScreenOrientation(
        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,
        statusBarVisible = false,
        navigationBarVisible = false,
        systemBarVisible = false
    )
    val scope = CoroutineScope(Dispatchers.IO)
    val context = LocalContext.current
    wearableViewModel.init(startReceiver = true)
    wearableViewModel.checkWearable(scope)
    val heartRate = wearableViewModel.heartRateState.collectAsState().value
    var showFinishWorkoutDialog by remember { mutableStateOf(false) }
    val videoState = videoViewModel.videoState.collectAsState().value
    videoViewModel.preventLock(videoViewModel.totalDuration * 2L)
    val videoControllerVisibility = remember { mutableStateOf(true) }
    val isShowingCompletedDialog = remember { mutableStateOf(false) }
    val isPlaying by videoState.isPlayTriggered.collectAsState()
    val isLoading by videoState.isLoading.collectAsState()
    val contentDuration by videoState.contentDuration.collectAsState()
    val contentDurationFixed by videoState.contentDurationFixed.collectAsState()
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
                            align = Alignment.TopCenter,
                            unbounded = true
                        )
                        .align(
                            alignment = Alignment.TopStart
                        )
                        .padding(
                            start = 10.dp,
                            top = 25.dp
                        )
                        .background(
                            color = Mirage_45,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    if (!videoViewModel.isPreview && videoControllerVisibility.value &&
                        videoViewModel.currentExercise != null &&
                        videoViewModel.currentExercise!!.toEnumExerciseCategoryType() ==
                        ExerciseCategoryType.Video
                    ) {
                        val totalTime = videoViewModel.currentTotalDuration - contentDuration
                        var totalTimePercent = 100f
                        if (totalTime > 0 && videoViewModel.totalDuration > 0) {
                            totalTimePercent =
                                (totalTime * 100 / videoViewModel.totalDuration).toFloat()
                            if (totalTimePercent > 100f) totalTimePercent = 100f
                        }
                        CountDownBox(
                            currentTimeInSeconds = contentDuration,
                            totalTimeInSeconds = totalTime,
                            totalTimePercent = totalTimePercent,
                            heartRate = heartRate.toString()
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .wrapContentSize(
                            align = Alignment.Center,
                            unbounded = true
                        )
                        .align(
                            alignment = Alignment.Center
                        )
                ) {
                    if (videoViewModel.currentExercise != null &&
                        videoViewModel.currentExercise!!.toEnumExerciseCategoryType() ==
                        ExerciseCategoryType.Rest
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val currentTime = contentDurationFixed - contentDuration
                            RestCountDownBox(currentTimeInSeconds = currentTime)
                            Spacer(modifier = Modifier.size(55.dp))
                        }
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
                if (videoControllerVisibility.value && videoViewModel.currentExercise != null &&
                    videoViewModel.currentExercise!!.toEnumExerciseCategoryType() !=
                    ExerciseCategoryType.Rest
                ) {
                    MainVideoControl(
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
                        isPlaying = isPlaying,
                        isPlayingLast = videoViewModel.isPlayingLast,
                        isPreview = videoViewModel.isPreview,
                        onInfoClick = {

                        },
                        onBackClick = {
                            videoViewModel.repeat()
                            if (videoViewModel.position > 0) {
                                Toast.makeText(
                                    context,
                                    context.getText(R.string.long_press_back),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onBackLongClick = {
                            videoViewModel.previous()
                        },
                        onPlayClick = {
                            videoViewModel.toggle()
                        },
                        onNextClick = {
                            videoViewModel.next()
                        },
                        onTvClick = {
                        },
                        onCloseClick = {
                            videoViewModel.pause()
                            showFinishWorkoutDialog = true
                        },
                    )
                } else if (videoControllerVisibility.value && videoViewModel.currentExercise != null &&
                    videoViewModel.currentExercise!!.toEnumExerciseCategoryType() ==
                    ExerciseCategoryType.Rest
                ) {
                    RestVideoControl(
                        nextExercise = videoViewModel.nextExercise,
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
                        isPlaying = isPlaying,
                        isPlayingLast = videoViewModel.isPlayingLast,
                        onPlayLongClick = {
                            videoViewModel.previous()
                        },
                        onPlayClick = {
                            videoViewModel.toggle()
                            Toast.makeText(
                                context,
                                context.getText(R.string.long_press_back),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onNextClick = {
                            videoViewModel.next()
                        },
                    )
                }
            }
            if (videoViewModel.exerciseCompleted) {
                ExerciseCompletedDialog(
                    openDialog = isShowingCompletedDialog,
                    onFinishClick = {
                        videoViewModel.onExerciseCompleted()
                    },
                    onRestartClick = {
                        videoViewModel.exerciseCompleted = false
                        videoViewModel.position = 0
                        videoViewModel.postInit()
                    }
                )
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
                    onDialogPositiveButtonClicked = { videoViewModel.navigateTo() },
                    onDismissRequest = {
                        showFinishWorkoutDialog = false
                    }
                )
            }
        }
    }
}
