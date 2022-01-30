package rhea.group.app.ui.screens.sleep_questionnaire

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import rhea.group.app.R
import rhea.group.app.components.ShowSnackbar
import rhea.group.app.components.SolidButton
import rhea.group.app.models.ErrorResponse
import rhea.group.app.models.Profile
import rhea.group.app.models.ViewState
import rhea.group.app.models.onError
import rhea.group.app.ui.screens.sleep_questionnaire.components.SleepCard
import rhea.group.app.ui.screens.sleep_questionnaire.viewModel.SleepQuestionnaireViewModel
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.DullLavender
import rhea.group.app.utils.sleepQuestions

@Composable
fun SleepQuestionnaireScreen(sleepQuestionnaireViewModel: SleepQuestionnaireViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val sleepQuestionnaireResponse =
        sleepQuestionnaireViewModel.sleepQuestionnaireResponse.collectAsState().value
    val preferencesState = sleepQuestionnaireViewModel.preferencesViewState.collectAsState().value
    val sleepQuestionnaireViewState =
        sleepQuestionnaireViewModel.sleepQuestionnaireViewState.collectAsState().value
    val sleepQuestionnaireNetworkState =
        sleepQuestionnaireViewModel.questionnaireRequest.collectAsState().value

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        sleepQuestionnaireNetworkState.onError {
            sleepQuestionnaireResponse?.let {
                LaunchedEffect((it as ErrorResponse).message) {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
            }
            ShowSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.Bottom)
                    .padding(bottom = 10.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when (preferencesState) {
                    is ViewState.Success -> {
                        Text(
                            text = "${
                                stringResource(id = R.string.hey).replace(
                                    ",",
                                    ""
                                )
                            } ${(preferencesState.success as Profile).firstName},",
                            style = MaterialTheme.typography.h5.copy(
                                color = Biscay,
                                lineHeight = 32.sp
                            )
                        )
                    }
                    else -> {
                        Text(
                            modifier = Modifier.shimmer(),
                            text = "${stringResource(id = R.string.hey)} ${stringResource(id = R.string.load)},",
                            style = MaterialTheme.typography.h5.copy(
                                color = Biscay,
                                lineHeight = 32.sp
                            )
                        )
                    }
                }
            }
            item {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.h3.copy(
                                color = Biscay,
                            ).toSpanStyle()
                        ) {
                            append(context.getString(R.string.its_time_to_complete_your))
                            append(" ")
                        }
                        withStyle(
                            style = MaterialTheme.typography.h3.copy(
                                color = DullLavender
                            ).toSpanStyle()
                        ) {
                            append(context.getString(R.string.sleep_hygiene))
                            append(" ")
                        }
                        withStyle(
                            style = MaterialTheme.typography.h3.copy(
                                color = Biscay
                            ).toSpanStyle()
                        ) {
                            append(context.getString(R.string.checklist))
                        }
                    },
                    textAlign = TextAlign.Center
                )
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
            when (sleepQuestionnaireViewState) {
                is ViewState.Success -> {
                    items(sleepQuestionnaireViewModel.answers.size) { index ->
                        val ans = sleepQuestionnaireViewModel.answers[sleepQuestions[index].id]
                        SleepCard(
                            modifier = Modifier
                                .height(180.dp)
                                .fillParentMaxWidth(),
                            elevation = 0.dp,
                            title = sleepQuestions[index].title,
                            subtitle = sleepQuestions[index].subtitle,
                            isPositive = ans,
                            onNegativeClick = {
                                sleepQuestionnaireViewModel.onClick(false, index)
                            },
                            onPositiveClick = {
                                sleepQuestionnaireViewModel.onClick(true, index)
                            }
                        )
                    }
                }
                else -> {
                    items(items = sleepQuestions) { sleep ->
                        SleepCard(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillParentMaxWidth()
                                .shimmer(),
                            elevation = 0.dp,
                            title = sleep.title,
                            subtitle = sleep.subtitle,
                            isPositive = null,
                            onNegativeClick = {},
                            onPositiveClick = {}
                        )
                    }
                }
            }
            item {
                SolidButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 25.dp, top = 20.dp, bottom = 20.dp)
                        .height(60.dp),
                    networkState = sleepQuestionnaireNetworkState,
                    color = DullLavender,
                    shape = RoundedCornerShape(33.dp),
                    icon = {},
                    enabled = !sleepQuestionnaireViewModel.checkEmptyQuestions,
                    title = stringResource(id = R.string.submit_questionnaire),
                    onClick = {
                        sleepQuestionnaireViewModel.completeSleepQuestionnaire()
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}