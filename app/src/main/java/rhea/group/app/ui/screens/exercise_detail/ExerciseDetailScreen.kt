package rhea.group.app.ui.screens.exercise_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import rhea.group.app.R
import rhea.group.app.ui.screens.exercise_detail.viewModel.ExerciseDetailViewModel
import rhea.group.app.ui.screens.stage.components.BackgroundLoader
import rhea.group.app.ui.theme.*
import rhea.group.app.utils.exercises

@Composable
fun ExerciseDetailScreen(exerciseDetailViewModel: ExerciseDetailViewModel = hiltViewModel()) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                modifier = Modifier.height(110.dp),
                title = {},
                backgroundColor = Transparent,
                contentColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = Botticelli,
                                shape = CircleShape
                            ),
                        onClick = { exerciseDetailViewModel.goBack() },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "Exit",
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(White)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    )
                    .clickable {
                               exerciseDetailViewModel.navigateTo()
                    },
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    BackgroundLoader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .border(
                                border = BorderStroke(
                                    width = 0.dp,
                                    color = Transparent
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        fadeIn = true,
                        enablePlaceHolder = true,
                        data = exercises[0].previewImageUrl
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_transparent_playbutton),
                        contentDescription = exercises[0].name,
                        modifier = Modifier
                            .size(60.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Text(
                exercises[0].name,
                style = MaterialTheme.typography.h5.copy(
                    lineHeight = 32.sp,
                    color = Biscay
                ),
                modifier = Modifier.padding(
                    vertical = 30.dp,
                    horizontal = 30.dp
                )
            )
            exercises[0].previewDescription.mapIndexed { index, it ->
                Text(
                    if (exercises[0].previewDescription.size > index) "$it\n" else it,
                    modifier = Modifier.padding(horizontal = 40.dp),
                    style = MaterialTheme.typography.body1.copy(
                        fontFamily = FtpPolar,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 22.sp,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = Biscay
                    )
                )
            }
        }
    }
}