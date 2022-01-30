package rhea.group.app.ui.screens.stage.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.models.Session
import rhea.group.app.models.Stage
import rhea.group.app.models.ViewState
import rhea.group.app.ui.screens.stage.viewModel.SessionCardViewModel
import rhea.group.app.ui.theme.SessionBackgroundColor

@Composable
fun SessionCard(
    sessionCardViewModel: SessionCardViewModel,
    stage: Stage,
    modifier: Modifier = Modifier
) {
    val sessionState = sessionCardViewModel.sessionState.collectAsState().value
    LaunchedEffect(key1 = true) {
        sessionCardViewModel.fetchSession(stage.currentSession)
    }
    Box(
        modifier = modifier
    ) {
        BackgroundLoader(
            modifier = Modifier.fillMaxSize(),
            fadeIn = true,
            enablePlaceHolder = true,
            data = stage.headerImageUrl
        )
        Box {
            Surface(color = SessionBackgroundColor, modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.up_next).uppercase(),
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 24.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                    when (sessionState) {
                        is ViewState.Success -> {
                            Text(
                                text = (sessionState.success as Session).name,
                                style = MaterialTheme.typography.h3.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start
                                ),
                            )
                        }
                        else -> {
                            Text(
                                text = stringResource(id = R.string.load),
                                style = MaterialTheme.typography.h3.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}