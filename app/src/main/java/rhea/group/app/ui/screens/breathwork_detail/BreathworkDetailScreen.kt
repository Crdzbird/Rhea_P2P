package rhea.group.app.ui.screens.breathwork_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import rhea.group.app.R
import rhea.group.app.components.SolidButton
import rhea.group.app.ui.screens.breathwork_detail.viewModel.BreathworkDetailViewModel
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.FtpPolar
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.utils.breathworkOptionParam

@Composable
fun BreathworkDetailScreen(
    breathworkDetailViewModel: BreathworkDetailViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = breathworkOptionParam.name,
                style = MaterialTheme.typography.h2.copy(
                    color = Biscay
                )
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                breathworkOptionParam.instructions?.let {
                    it.mapIndexed { index, s ->
                        Text(
                            if (it.size > index) "• $s\n" else s,
                            style = MaterialTheme.typography.body1.copy(
                                fontFamily = FtpPolar,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 22.sp,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Start,
                                color = Biscay
                            )
                        )
                    }
                }
            }
            SolidButton(
                modifier = Modifier
                    .fillMaxWidth(
                        fraction = .7f
                    )
                    .fillMaxHeight(
                        fraction = .4f
                    ),
                color = Turquoise,
                shape = RoundedCornerShape(33.dp),
                icon = {},
                enabled = true,
                title = stringResource(id = R.string.continue_rhea),
                onClick = { breathworkDetailViewModel.navigateTo() }
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}