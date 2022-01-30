package rhea.group.app.ui.screens.stage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.valentinilk.shimmer.shimmer
import rhea.group.app.R
import rhea.group.app.components.CircularProgressBar
import rhea.group.app.components.ConnectivityStatus
import rhea.group.app.components.EquipmentFlow
import rhea.group.app.components.LoadAsset
import rhea.group.app.models.*
import rhea.group.app.ui.screens.stage.components.NextStageCard
import rhea.group.app.ui.screens.stage.components.SessionListTile
import rhea.group.app.ui.screens.stage.viewModel.StageViewModel
import rhea.group.app.ui.theme.*

@Composable
fun StageTabScreen(
    stageViewModel: StageViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val profileState = stageViewModel.profileState.collectAsState().value
    val stageState = stageViewModel.stageState.collectAsState().value
    val sessionState = stageViewModel.sessionState.collectAsState().value
    val isRefreshingState = stageViewModel.isRefreshingState.collectAsState().value
    Surface(
        color = MaterialTheme.colors.background
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshingState),
            onRefresh = {
                stageViewModel.onRefresh()
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp),
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    ConnectivityStatus()
                }
                item {
                    Image(
                        painter = painterResource(id = R.drawable.ic_rhea),
                        contentDescription = stringResource(id = R.string.rhea),
                        modifier = Modifier
                            .size(width = 104.92.dp, height = 50.dp)
                            .padding(top = 6.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                item {
                    when (profileState) {
                        is ViewState.Success -> {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = MaterialTheme.typography.h1.copy(
                                            color = Biscay
                                        ).toSpanStyle()
                                    ) {
                                        append(context.getString(R.string.hey))
                                        append("\n")
                                    }
                                    withStyle(
                                        style = MaterialTheme.typography.h1.copy(
                                            color = Turquoise
                                        ).toSpanStyle()
                                    ) {
                                        append((profileState.success as Profile).firstName)
                                    }
                                },
                                modifier = Modifier.padding(top = 45.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                        else -> {
                            Text(
                                modifier = Modifier
                                    .padding(top = 45.dp)
                                    .shimmer(),
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = MaterialTheme.typography.h1.copy(
                                            color = Biscay
                                        ).toSpanStyle()
                                    ) {
                                        append(context.getString(R.string.hey))
                                        append("\n")
                                    }
                                    withStyle(
                                        style = MaterialTheme.typography.h1.copy(
                                            color = Turquoise
                                        ).toSpanStyle()
                                    ) {
                                        append(context.getString(R.string.load))
                                    }
                                },
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item {
                    when (stageState) {
                        is ViewState.Success -> {
                            val stage = stageState.success as Stage
                            Text(
                                text =
                                stage.motivationalText ?: stringResource(id = R.string.no_progress),
                                style = MaterialTheme.typography.h6.copy(color = Hoki)
                            )
                        }
                        else -> {
                            Text(
                                modifier = Modifier.shimmer(),
                                text = stringResource(id = R.string.load),
                                style = MaterialTheme.typography.h6.copy(color = Hoki)
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(40.dp)) }
                when (sessionState) {
                    is ViewState.Success -> {
                        item {
                            NextStageCard(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .background(
                                        color = Biscay,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                    .clickable {
                                        val stageSessions = sessionState.success as List<*>
                                        val stageSession =
                                            stageSessions.find { ss -> (ss as StageSession).isActive } as StageSession
                                        if (stageSession.category == Category.Sleep) {
                                            stageSession.sleepQuestions?.let {
                                                stageViewModel.navigateAndSetQuestion(it, stageSession)
                                            }
                                        } else {
                                            stageViewModel.navigateToSessionDetail(stageSession)
                                        }
                                    },
                                leading = {
                                    LoadAsset(
                                        modifier = Modifier
                                            .padding(end = 16.dp),
                                        asset = R.drawable.ic_play,
                                        description = stringResource(id = R.string.capability_phone),
                                        colorFilter = ColorFilter.tint(Turquoise)
                                    )
                                },
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.begin_next_session),
                                        style = MaterialTheme.typography.button.copy(
                                            color = White
                                        )
                                    )
                                },
                            )
                        }
                    }
                    else -> {
                        item {
                            NextStageCard(
                                modifier = Modifier
                                    .background(
                                        color = Biscay,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                    .shimmer(),
                                leading = {
                                    LoadAsset(
                                        modifier = Modifier
                                            .padding(end = 16.dp),
                                        asset = R.drawable.ic_play,
                                        description = stringResource(id = R.string.capability_phone),
                                        colorFilter = ColorFilter.tint(Turquoise)
                                    )
                                },
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.begin_next_session),
                                        style = MaterialTheme.typography.button.copy(
                                            color = White
                                        )
                                    )
                                },
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(40.dp)) }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        when (stageState) {
                            is ViewState.Success -> {
                                val stage = stageState.success as Stage
                                val stageSessionList = stage.combine()
                                val progress = stage.progress(stageSessionList)
                                CircularProgressBar(
                                    modifier = Modifier
                                        .size(290.dp)
                                        .shadow(
                                            elevation = 9.dp,
                                            shape = CircleShape
                                        ),
                                    progress = progress.toFloat(),
                                    progressMax = 100f,
                                    progressBarColor = Turquoise,
                                    progressBarWidth = 40.dp,
                                    backgroundProgressBarColor = LinkWater,
                                    backgroundProgressBarWidth = 40.dp,
                                    roundBorder = true,
                                    startAngle = 0f,
                                    content = {
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "$progress%",
                                                style = MaterialTheme.typography.h1.copy(
                                                    fontSize = 60.sp,
                                                    lineHeight = 76.sp,
                                                    color = Turquoise
                                                )
                                            )
                                            Text(
                                                text = stringResource(id = R.string.complete).uppercase(),
                                                style = MaterialTheme.typography.body1.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    lineHeight = 24.sp,
                                                    color = Turquoise
                                                )
                                            )
                                        }
                                    }
                                )
                            }
                            else -> {
                                CircularProgressBar(
                                    modifier = Modifier
                                        .size(290.dp)
                                        .shimmer(),
                                    progress = 100f,
                                    progressMax = 100f,
                                    progressBarColor = Turquoise,
                                    progressBarWidth = 40.dp,
                                    backgroundProgressBarColor = LinkWater,
                                    backgroundProgressBarWidth = 40.dp,
                                    roundBorder = true,
                                    startAngle = 0f,
                                    content = {
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.current_stage).uppercase(),
                                                style = MaterialTheme.typography.body2
                                            )
                                            Text(
                                                text = "100%",
                                                style = MaterialTheme.typography.h1.copy(
                                                    fontSize = 60.sp,
                                                    lineHeight = 76.sp,
                                                    color = Turquoise
                                                )
                                            )
                                            Text(
                                                text = stringResource(id = R.string.complete).uppercase(),
                                                style = MaterialTheme.typography.body1.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    lineHeight = 24.sp,
                                                    color = Turquoise
                                                )
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(40.dp)) }
                when (sessionState) {
                    is ViewState.Success -> {
                        val stageSessions = sessionState.success as List<*>
                        items(items = stageSessions) { ss ->
                            val stageSession = ss as StageSession
                            SessionListTile(
                                isSelected = stageSession.isActive,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(vertical = 10.0.dp)
                                    .shadow(
                                        elevation = 2.dp,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable {
                                        if (!stageSession.isActive) return@clickable
                                        when (stageSession.category) {
                                            Category.Sleep -> {
                                                stageSession.sleepQuestions?.let {
                                                    stageViewModel.navigateAndSetQuestion(it, stageSession)
                                                }
                                            }
                                            Category.Base -> stageViewModel.navigateToSessionDetail(stageSession)
                                            Category.Unknown -> stageViewModel.navigateToSessionDetail(stageSession)
                                        }
                                        //HUDDLE: @history*GALAXY52@
                                        //-G:  .7realize;JUMPED3
                                    },
                                leading = {
                                    LoadAsset(
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 9.dp, bottom = 9.dp),
                                        asset = if (stageSession.feeling == null) {
                                            categoryIconResource(
                                                category = stageSession.category
                                            )
                                        } else {
                                            println("feeling: ${stageSession.feeling}")
                                            feelingIconResource(feelingsType = stageSession.feeling)
                                        },
                                        description = stringResource(id = R.string.load),
                                        colorFilter = when {
                                            stageSession.isCompleted -> {
                                                if (stageSession.feeling == null) {
                                                    if (stageSession.category == Category.Sleep) {
                                                        ColorFilter.tint(DullLavender)
                                                    } else {
                                                        ColorFilter.tint(Turquoise)
                                                    }
                                                } else {
                                                    null
                                                }
                                            }
                                            stageSession.isActive -> ColorFilter.tint(White)
                                            else -> ColorFilter.tint(Botticelli)
                                        }
                                    )
                                },
                                content = {
                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = stageSession.session
                                            ?: stringResource(id = R.string.rhea),
                                        style = MaterialTheme.typography.button.copy(
                                            color = when {
                                                stageSession.isCompleted -> Black
                                                stageSession.isActive -> White
                                                else -> Botticelli
                                            }
                                        )
                                    )
                                },
                                trailing = {
                                    if (stageSession.isCompleted) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth()
                                                .padding(top = 9.dp, bottom = 9.dp, end = 16.dp),
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            LoadAsset(
                                                modifier = Modifier.size(
                                                    width = 20.0.dp,
                                                    height = 20.0.dp
                                                ),
                                                asset = R.drawable.ic_check,
                                                description = stringResource(id = R.string.complete),
                                                colorFilter = null
                                            )
                                            Text(
                                                text = stageSession.dateToFormatMMMdd(),
                                                style = MaterialTheme.typography.overline.copy(
                                                    fontSize = 10.sp,
                                                    lineHeight = 18.sp,
                                                    color = Botticelli
                                                )
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                    else -> {
                        items(count = 3) {
                            SessionListTile(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .padding(vertical = 10.0.dp)
                                    .shimmer(),
                                leading = {
                                    LoadAsset(
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 9.dp, bottom = 9.dp),
                                        asset = R.drawable.ic_play,
                                        description = stringResource(id = R.string.load),
                                        colorFilter = ColorFilter.tint(Turquoise)
                                    )
                                },
                                content = {
                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = stringResource(id = R.string.load),
                                        style = MaterialTheme.typography.button
                                    )
                                },
                                trailing = {}
                            )
                        }
                    }
                }
                when (stageState) {
                    is ViewState.Success -> {
                        val stage = stageState.success as Stage
                        item {
                            Text(
                                modifier = Modifier.padding(top = 50.dp, bottom = 10.dp),
                                text = stringResource(id = R.string.additional_breathwork).uppercase(),
                                style = MaterialTheme.typography.button.copy(
                                    color = Biscay,
                                    textAlign = TextAlign.Start,
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier.padding(bottom = 25.dp),
                                text = stage.closingMotivationalText
                                    ?: stringResource(id = R.string.breathwork),
                                style = MaterialTheme.typography.body1.copy(
                                    color = Hoki,
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                        item {
                            SessionListTile(
                                isSelected = false,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(vertical = 10.0.dp)
                                    .shadow(
                                        elevation = 2.dp,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable {
                                        if (stage.additionalBreathworkSession.isNullOrEmpty()) return@clickable
                                        stageViewModel.navigateToBreathworkScreen(stage.additionalBreathworkSession)
                                    },
                                leading = {
                                    LoadAsset(
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 9.dp, bottom = 9.dp),
                                        asset = R.drawable.ic_play,
                                        description = stringResource(id = R.string.load),
                                        colorFilter = ColorFilter.tint(Biscay)
                                    )
                                },
                                content = {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(
                                                fraction = .55f
                                            )
                                            .padding(start = 16.dp),
                                        text = stringResource(id = R.string.breathwork),
                                        style = MaterialTheme.typography.button.copy(color = Biscay)
                                    )
                                },
                                trailing = {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth()
                                            .padding(top = 9.dp, bottom = 9.dp, end = 16.dp),
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Badge(
                                            modifier = Modifier.clip(RoundedCornerShape(33.dp)),
                                            backgroundColor = Botticelli
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        horizontal = 20.dp,
                                                        vertical = 7.dp
                                                    ),
                                                text = stringResource(id = R.string.optional),
                                                style = MaterialTheme.typography.button.copy(
                                                    fontSize = 12.sp
                                                )
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                    else -> {
                        item {
                            Text(
                                modifier = Modifier
                                    .padding(top = 50.dp, bottom = 10.dp)
                                    .shimmer(),
                                text = stringResource(id = R.string.additional_breathwork).uppercase(),
                                style = MaterialTheme.typography.button.copy(
                                    color = Biscay,
                                    textAlign = TextAlign.Start,
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 25.dp)
                                    .shimmer(),
                                text = stringResource(id = R.string.breathwork),
                                style = MaterialTheme.typography.body1.copy(
                                    color = Hoki,
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                        item {
                            SessionListTile(
                                isSelected = false,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(vertical = 10.0.dp)
                                    .shadow(
                                        elevation = 2.dp,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .shimmer(),
                                leading = {
                                    LoadAsset(
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 9.dp, bottom = 9.dp)
                                            .shimmer(),
                                        asset = R.drawable.ic_play,
                                        description = stringResource(id = R.string.load),
                                        colorFilter = ColorFilter.tint(Biscay)
                                    )
                                },
                                content = {
                                    Text(
                                        modifier = Modifier
                                            .shimmer()
                                            .padding(start = 16.dp),
                                        text = stringResource(id = R.string.breathwork),
                                        style = MaterialTheme.typography.button.copy(color = Biscay)
                                    )
                                },
                                trailing = {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth()
                                            .padding(top = 9.dp, bottom = 9.dp, end = 16.dp)
                                            .shimmer(),
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Badge(
                                            modifier = Modifier.clip(RoundedCornerShape(33.dp)),
                                            backgroundColor = Botticelli
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = 20.dp,
                                                        vertical = 7.dp
                                                    )
                                                    .shimmer(),
                                                text = stringResource(id = R.string.optional),
                                                style = MaterialTheme.typography.button.copy(
                                                    fontSize = 12.sp
                                                )
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
                when (sessionState) {
                    is ViewState.Success -> {
                        val stageSessions = sessionState.success as List<*>
                        val stageSession =
                            stageSessions.find { ss -> (ss as StageSession).isActive } as StageSession
                        item {
                            Text(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(
                                        top = 30.dp,
                                        bottom = 12.dp
                                    ),
                                text = stringResource(id = R.string.current_stage).uppercase(),
                                style = MaterialTheme.typography.body2.copy(
                                    color = Biscay,
                                    textAlign = TextAlign.Center,
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(bottom = 30.dp),
                                text = stageSession.session ?: stringResource(id = R.string.load),
                                style = MaterialTheme.typography.h5.copy(
                                    color = Turquoise,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 32.sp
                                )
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(bottom = 12.dp),
                                text = stringResource(id = R.string.recommended_time_to_complete).uppercase(),
                                style = MaterialTheme.typography.body2.copy(
                                    color = Biscay,
                                    textAlign = TextAlign.Center,
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(bottom = 30.dp),
                                text = stageSession.recommendedTime
                                    ?: stringResource(id = R.string.load),
                                style = MaterialTheme.typography.h5.copy(
                                    color = Turquoise,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 32.sp
                                )
                            )
                        }
                        item {
                            EquipmentFlow(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(
                                        top = 15.dp,
                                        bottom = 8.dp,
                                        start = 8.dp,
                                        end = 8.dp
                                    ),
                                equipments = stageSession.equipments,
                                background = AlbescentWhiteOpacity
                            )
                        }
                    }
                    else -> {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(
                                        top = 30.dp,
                                        bottom = 12.dp
                                    )
                                    .shimmer(),
                                text = stringResource(id = R.string.current_stage).uppercase(),
                                style = MaterialTheme.typography.body2.copy(
                                    color = Biscay,
                                    textAlign = TextAlign.Center,
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(bottom = 30.dp)
                                    .shimmer(),
                                text = stringResource(id = R.string.load),
                                style = MaterialTheme.typography.h5.copy(
                                    color = Turquoise,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 32.sp
                                )
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(bottom = 12.dp)
                                    .shimmer(),
                                text = stringResource(id = R.string.recommended_time_to_complete).uppercase(),
                                style = MaterialTheme.typography.body2.copy(
                                    color = Biscay,
                                    textAlign = TextAlign.Center,
                                    textDecoration = TextDecoration.Underline
                                )
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(bottom = 30.dp)
                                    .shimmer(),
                                text = stringResource(id = R.string.load),
                                style = MaterialTheme.typography.h5.copy(
                                    color = Turquoise,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 32.sp
                                )
                            )
                        }
                        item {
                            EquipmentFlow(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(
                                        top = 15.dp,
                                        bottom = 8.dp,
                                        start = 8.dp,
                                        end = 8.dp
                                    )
                                    .shimmer(),
                                equipments = listOf(
                                    stringResource(id = R.string.load),
                                    stringResource(id = R.string.load)
                                ),
                                background = AlbescentWhiteOpacity
                            )
                        }
                    }
                }
            }
        }
    }
}