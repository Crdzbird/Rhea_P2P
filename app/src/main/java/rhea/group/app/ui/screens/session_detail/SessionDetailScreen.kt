package rhea.group.app.ui.screens.session_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import rhea.group.app.R
import rhea.group.app.components.EquipmentFlow
import rhea.group.app.components.LoadAsset
import rhea.group.app.components.SolidButton
import rhea.group.app.models.ExerciseCategoryType
import rhea.group.app.models.Session
import rhea.group.app.models.ViewState
import rhea.group.app.ui.screens.session_detail.components.CardHitBox
import rhea.group.app.ui.screens.session_detail.viewModel.SessionDetailViewModel
import rhea.group.app.ui.screens.stage.components.BackgroundLoader
import rhea.group.app.ui.theme.*
import rhea.group.app.utils.exercises
import rhea.group.app.utils.toMinutesAndSeconds

@Composable
fun SessionDetailScreen(sessionDetailViewModel: SessionDetailViewModel = hiltViewModel()) {
    val sessionState = sessionDetailViewModel.sessionState.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        val session = when (sessionState) {
            is ViewState.Success -> {
                sessionState.success as Session
            }
            else -> {
                Session()
            }
        }
        LazyColumn(
            modifier = if (
                sessionState is ViewState.Error
                || sessionState is ViewState.Loading
                || sessionState is ViewState.Unauthorized
            ) Modifier
                .fillMaxSize()
                .shimmer()
            else Modifier
                .fillMaxSize()
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 25.dp,
                            end = 25.dp,
                            top = 25.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = session.name,
                        style = MaterialTheme.typography.button.copy(
                            color = Turquoise
                        )
                    )

                    Text(
                        text = "${stringResource(id = R.string.session)} ${session.no}",
                        style = MaterialTheme.typography.h2.copy(
                            color = Biscay
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    IconButton(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                color = Turquoise,
                                shape = CircleShape
                            ),
                        onClick = {
                            session.videoSections?.let {
                                exercises =
                                    it.flatMap { vs -> vs.exercises }.toList()
                                sessionDetailViewModel.navigateTo(session)
                            }
                        },
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            modifier = Modifier.fillMaxSize(fraction = 0.8f),
                            contentDescription = "Play",
                            tint = White
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = session.brief,
                        style = MaterialTheme.typography.h6.copy(
                            color = Biscay,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(52.dp))
                    Text(
                        text = session.description,
                        style = MaterialTheme.typography.body1.copy(
                            fontFamily = FtpPolar,
                            color = Biscay,
                            lineHeight = 22.sp,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                    Spacer(modifier = Modifier.height(43.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(175.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        CardHitBox(
                            modifier = Modifier
                                .fillParentMaxHeight()
                                .fillParentMaxWidth(fraction = .45f)
                                .padding(end = 5.dp),
                            elevation = 3.dp,
                            leading = {
                                LoadAsset(
                                    asset = R.drawable.ic_timer,
                                    description = stringResource(id = R.string.duration),
                                    colorFilter = ColorFilter.tint(Turquoise)
                                )
                            },
                            content = {
                                Text(
                                    text = stringResource(id = R.string.duration),
                                    style = MaterialTheme.typography.caption.copy(
                                        color = Biscay
                                    )
                                )
                            },
                            trailing = {
                                Text(
                                    text = session.duration.toMinutesAndSeconds(),
                                    style = MaterialTheme.typography.h2.copy(
                                        color = Turquoise,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        )
                        CardHitBox(
                            modifier = Modifier
                                .fillParentMaxHeight()
                                .fillParentMaxWidth(fraction = .5f)
                                .padding(start = 5.dp),
                            elevation = 3.dp,
                            leading = {
                                LoadAsset(
                                    asset = R.drawable.ic_heart,
                                    description = stringResource(id = R.string.target_heart_rate),
                                    colorFilter = ColorFilter.tint(Persimmom)
                                )
                            },
                            content = {
                                Text(
                                    text = stringResource(id = R.string.target_heart_rate),
                                    style = MaterialTheme.typography.caption.copy(
                                        color = Biscay
                                    )
                                )
                            },
                            trailing = {
                                Text(
                                    text = "${session.targetHeartRate}",
                                    style = MaterialTheme.typography.h2.copy(
                                        color = Persimmom,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(52.dp))
                    EquipmentFlow(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(8.dp),
                        equipments = session.equipment,
                        background = WhiteLilac
                    )
                    Spacer(modifier = Modifier.height(45.dp))
                }

            }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = White,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        session.videoSections?.let {
                            it.mapIndexed { index, vs ->
                                Spacer(modifier = Modifier.height(45.dp))
                                Text(
                                    text = "${1 + index}. ${vs.name}",
                                    style = MaterialTheme.typography.h6.copy(
                                        lineHeight = 32.sp,
                                        color = Biscay
                                    ),
                                    textDecoration = TextDecoration.Underline
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = vs.detail,
                                    style = MaterialTheme.typography.body2
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                vs.exercises.mapIndexed { exIndex, ex ->
                                    when (ex.toEnumExerciseCategoryType()) {
                                        ExerciseCategoryType.Rest -> {
                                            Spacer(modifier = Modifier.size(0.dp))
                                        }
                                        ExerciseCategoryType.Video -> {
                                            Surface(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = 25.dp,
                                                        end = 25.dp,
                                                        top = 10.dp
                                                    )
                                                    .clickable {
                                                        exercises = vs.exercises.subList(
                                                            exIndex,
                                                            vs.exercises.size
                                                        )
                                                        sessionDetailViewModel.navigateToExerciseDetail()
                                                    },
                                                color = LinkWater,
                                                shape = RoundedCornerShape(20.dp)
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        BackgroundLoader(
                                                            modifier = Modifier
                                                                .size(80.dp)
                                                                .border(
                                                                    border = BorderStroke(
                                                                        width = 0.dp,
                                                                        color = Transparent
                                                                    ),
                                                                    shape = RoundedCornerShape(
                                                                        topStart = 20.dp,
                                                                        bottomStart = 20.dp
                                                                    )
                                                                ),
                                                            fadeIn = true,
                                                            enablePlaceHolder = true,
                                                            data = ex.previewImageUrl
                                                        )
                                                        Image(
                                                            painter = painterResource(id = R.drawable.ic_transparent_playbutton),
                                                            contentDescription = ex.name,
                                                            modifier = Modifier
                                                                .size(30.dp),
                                                            contentScale = ContentScale.Fit
                                                        )
                                                    }
                                                    Text(
                                                        modifier = Modifier.padding(start = 10.dp),
                                                        text = ex.name,
                                                        style = MaterialTheme.typography.body2.copy(
                                                            color = Biscay
                                                        )
                                                    )
                                                    Spacer(modifier = Modifier.weight(1.0f))
                                                    Text(
                                                        modifier = Modifier.padding(end = 20.dp),
                                                        text = ex.duration.toMinutesAndSeconds(),
                                                        style = MaterialTheme.typography.body2.copy(
                                                            color = Botticelli
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        Spacer(modifier = Modifier.height(43.dp))
                        SolidButton(
                            color = Turquoise,
                            modifier = Modifier
                                .fillParentMaxWidth(fraction = .65f)
                                .height(55.dp),
                            shape = RoundedCornerShape(50),
                            icon = {},
                            title = stringResource(id = R.string.begin_session)
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        }
    }
}