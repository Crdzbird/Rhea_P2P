package rhea.group.app.ui.screens.symptoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rhea.group.app.R
import rhea.group.app.components.SolidButton
import rhea.group.app.models.FeelingsType
import rhea.group.app.ui.screens.symptoms.viewModel.SymptomsViewModel
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.ui.theme.White

@Composable
fun SymptomsScreen(
//    navController: NavController,
//    session: String,
//    duration: Int? = 0,
    symptomsViewModel: SymptomsViewModel = hiltViewModel(),
//    breathwork_type: String? = null
) {
    val reasonOptions: List<Map<String, Any>> = listOf(
        mapOf(
            "name" to stringResource(id = R.string.better),
            "enum" to FeelingsType.positive,
            "icon" to R.drawable.ic_better
        ),
        mapOf(
            "name" to stringResource(id = R.string.same),
            "enum" to FeelingsType.neutral,
            "icon" to R.drawable.ic_same
        ),
        mapOf(
            "name" to stringResource(id = R.string.worse),
            "enum" to FeelingsType.negative,
            "icon" to R.drawable.ic_worse
        )
    )
    val onSelectionChange = { text: FeelingsType ->
        symptomsViewModel.selectedOptionReason.value = text.name
    }
    Scaffold(
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.session_complete).uppercase(),
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body2.copy(
                    color = Turquoise,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = stringResource(id = R.string.how_are_your_symptoms),
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 5.dp),
                style = MaterialTheme.typography.h3.copy(
                    color = Biscay
                )
            )
            Spacer(modifier = Modifier.height(39.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                reasonOptions.map {
                    Box(
                        modifier = Modifier
                            .size(105.dp)
                            .clip(
                                shape = RoundedCornerShape(size = 33.dp),
                            )
                            .clickable {
                                onSelectionChange(it["enum"] as FeelingsType)
                            }
                            .background(
                                if ((it["enum"] as FeelingsType).name == symptomsViewModel.selectedOptionReason.value)
                                    Turquoise
                                else
                                    White
                            )
                    ) {
                        Column(
                            modifier = Modifier.align(
                                alignment = Alignment.Center
                            ),
                        ) {
                            Image(
                                painter = painterResource(id = it["icon"] as Int),
                                contentDescription = it["name"] as String,
                                modifier = Modifier
                                    .size(60.dp)
                                    .align(Alignment.CenterHorizontally),
                                contentScale = ContentScale.Fit,
                                colorFilter = if ((it["enum"] as FeelingsType).name == symptomsViewModel.selectedOptionReason.value) {
                                    ColorFilter.tint(
                                        color = White
                                    )
                                } else {
                                    null
                                }
                            )
                            Text(
                                text = it["name"] as String,
                                style = MaterialTheme.typography.body2.copy(
                                    color = if ((it["enum"] as FeelingsType).name == symptomsViewModel.selectedOptionReason.value) White else Biscay,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .align(
                                        Alignment.CenterHorizontally
                                    )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(55.dp))
            if (symptomsViewModel.selectedOptionReason.value.isNotEmpty()) {
                SolidButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 103.dp)
                        .height(60.dp),
                    color = Turquoise,
                    shape = RoundedCornerShape(33.dp),
                    icon = {},
                    title = stringResource(id = R.string.complete_session),
                    onClick = {
                        symptomsViewModel.completeWorkout()
                    }
                )
            }
        }
    }
}