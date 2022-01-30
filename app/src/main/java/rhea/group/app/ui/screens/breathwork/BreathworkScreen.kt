package rhea.group.app.ui.screens.breathwork

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import rhea.group.app.R
import rhea.group.app.models.BreathworkOption
import rhea.group.app.models.Session
import rhea.group.app.models.ViewState
import rhea.group.app.ui.screens.breathwork.components.BreathCard
import rhea.group.app.ui.screens.breathwork.viewModel.BreathworkViewModel
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.FtpPolar
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.utils.breathworkOptionParam

@Composable
fun BreathworkScreen(breathworkViewModel: BreathworkViewModel = hiltViewModel()) {
    val breathworkState = breathworkViewModel.breathworkState.collectAsState().value
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 25.dp,
                    end = 25.dp,
                    top = it.calculateTopPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (breathworkState) {
                is ViewState.Success -> {
                    val prefSession = breathworkState.success as Session
                    item {
                        Text(
                            modifier = Modifier.padding(bottom = 7.dp),
                            text = stringResource(id = R.string.care),
                            style = MaterialTheme.typography.button.copy(
                                color = Turquoise
                            )
                        )
                    }
                    item {
                        Text(
                            text = prefSession.name,
                            style = MaterialTheme.typography.h2.copy(
                                color = Biscay
                            )
                        )
                    }
                    item {
                        Text(
                            modifier = Modifier.padding(vertical = 13.dp),
                            text = prefSession.brief.ifEmpty { stringResource(id = R.string.breathwork_description) },
                            style = MaterialTheme.typography.h6.copy(
                                color = Biscay,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                    item {
                        Text(
                            text = prefSession.description.ifEmpty { stringResource(id = R.string.breathwork_description_2) },
                            style = MaterialTheme.typography.body1.copy(
                                fontFamily = FtpPolar,
                                color = Biscay,
                                lineHeight = 22.sp,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Start
                            )
                        )
                    }
                    items(items = prefSession.breathworkOptions!!) { breathworkOption ->
                        BreathCard(
                            breathworkOption = breathworkOption,
                            onTap = {
                                breathworkOptionParam = breathworkOption
                                breathworkViewModel.navigateTo()
                            }
                        )
                    }
                }
                else -> {
                    item {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 7.dp)
                                .shimmer(),
                            text = stringResource(id = R.string.care),
                            style = MaterialTheme.typography.button.copy(
                                color = Turquoise
                            )
                        )
                    }
                    item {
                        Text(
                            modifier = Modifier.shimmer(),
                            text = stringResource(id = R.string.load),
                            style = MaterialTheme.typography.h2.copy(
                                color = Biscay
                            )
                        )
                    }
                    items(count = 3) {
                        BreathCard(
                            breathworkOption = BreathworkOption(),
                            onTap = {}
                        )
                    }
                }
            }
        }
    }
}