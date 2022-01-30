package rhea.group.app.ui.screens.breath_question_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rhea.group.app.R
import rhea.group.app.components.NumberPicker
import rhea.group.app.components.SolidButton
import rhea.group.app.ui.screens.breath_question_screen.viewModel.BreathQuestionViewModel
import rhea.group.app.ui.theme.*
import rhea.group.app.utils.breathworkOptionParam

@Composable
fun BreathQuestionScreen(
    breathQuestionViewModel: BreathQuestionViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                        onClick = {
                            breathQuestionViewModel.goBack()
                        },
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
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 15.dp),
                text = stringResource(id = R.string.breathwork_duration_question),
                style = MaterialTheme.typography.h3.copy(
                    color = Biscay
                )
            )
            NumberPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(
                        fraction = .15f
                    )
                    .padding(horizontal = 10.dp),
                min = 0,
                max = breathworkOptionParam.maxMins,
                default = breathQuestionViewModel.time.value,
                onValueChange = { num -> breathQuestionViewModel.time.value = num.toInt() }
            )
            SolidButton(
                modifier = Modifier
                    .padding(
                        start = 25.dp,
                        end = 25.dp,
                        top = 30.dp,
                        bottom = 30.dp
                    ),
                color = Turquoise,
                shape = RoundedCornerShape(33.dp),
                icon = {},
                enabled = breathQuestionViewModel.time.value > 0,
                title = stringResource(id = R.string.begin_session),
                onClick = { breathQuestionViewModel.navigateTo() }
            )
        }
    }
}