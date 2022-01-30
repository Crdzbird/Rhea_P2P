package rhea.group.app.ui.screens.ending_workout_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rhea.group.app.R
import rhea.group.app.components.SolidButton
import rhea.group.app.models.Reason
import rhea.group.app.models.ReasonType
import rhea.group.app.ui.screens.ending_workout_screen.viewModel.EndingWorkoutViewModel
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.ui.theme.White

@Composable
fun EndingWorkoutScreen(endingWorkoutViewModel: EndingWorkoutViewModel = hiltViewModel()) {
    val selectedOptionReason = remember { mutableStateOf("") }
    val reasonOptions: List<Map<String, Any>> = listOf(
        mapOf(
            "name" to stringResource(id = R.string.workout_was_too_difficult),
            "enum" to ReasonType.reason_too_difficult
        ),
        mapOf(
            "name" to stringResource(id = R.string.symptoms_have_worsened),
            "enum" to ReasonType.reason_feeling_worse
        ),
        mapOf(
            "name" to stringResource(id = R.string.would_like_to_continue_later),
            "enum" to ReasonType.reason_retry_later
        ),
        mapOf(
            "name" to stringResource(id = R.string.other),
            "enum" to ReasonType.reason_other
        )
    )
    val onSelectionChange = { text: ReasonType ->
        selectedOptionReason.value = text.name
    }
    Scaffold(backgroundColor = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.you_ended_workout_early),
                modifier = Modifier.padding(horizontal = 25.dp),
                style = MaterialTheme.typography.h3.copy(
                    color = Biscay
                )
            )
            Spacer(modifier = Modifier.height(17.dp))
            Text(
                text = stringResource(id = R.string.please_tell_us_why),
                modifier = Modifier.padding(horizontal = 25.dp),
                style = MaterialTheme.typography.h6.copy(
                    color = Biscay,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(44.dp))
            reasonOptions.map {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 50.dp, vertical = 5.dp)
                            .clip(
                                shape = RoundedCornerShape(size = 33.dp),
                            )
                            .clickable {
                                onSelectionChange(it["enum"] as ReasonType)
                            }
                            .background(
                                if ((it["enum"] as ReasonType).name == selectedOptionReason.value) Biscay else White
                            )
                    ) {
                        Text(
                            text = it["name"] as String,
                            style = MaterialTheme.typography.button.copy(
                                color = if ((it["enum"] as ReasonType).name == selectedOptionReason.value) White else Biscay,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.align(
                                alignment = Alignment.Center
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(55.dp))
            if (selectedOptionReason.value.isNotEmpty()) {
                SolidButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 103.dp)
                        .height(60.dp),
                    color = Turquoise,
                    shape = RoundedCornerShape(33.dp),
                    icon = {},
                    title = stringResource(id = R.string.end_workout),
                    onClick = {
                        endingWorkoutViewModel.endWorkout(
                            reason = Reason(
                                ReasonType.valueOf(selectedOptionReason.value)
                            )
                        )
                    }
                )
            }
        }
    }
}