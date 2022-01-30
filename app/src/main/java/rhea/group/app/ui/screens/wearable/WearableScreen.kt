package rhea.group.app.ui.screens.wearable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rhea.group.app.R
import rhea.group.app.components.WearableSelector
import rhea.group.app.ui.theme.White

@Composable
fun WearableScreen() {
    Scaffold(backgroundColor = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .align(
                        alignment = Alignment.TopCenter
                    )
                    .fillMaxHeight(fraction = 0.4f)
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.wearable),
                    contentDescription = "Wearable",
                    contentScale = ContentScale.FillBounds
                )
            }
            Box(
                modifier = Modifier
                    .align(
                        alignment = Alignment.BottomCenter
                    )
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .fillMaxHeight(fraction = 0.7f)
                    .fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    color = MaterialTheme.colors.background
                ) {
                    WearableSelector(
                        header = stringResource(id = R.string.wearable_question),
                        explanation = stringResource(id = R.string.wearable_description),
                        bottom = stringResource(id = R.string.zero_wearable)
                    )
                }
                Surface(
                    modifier = Modifier
                        .size(80.dp)
                        .align(
                            alignment = Alignment.TopCenter
                        )
                        .offset(
                            y = (-40).dp
                        ),
                    shape = CircleShape,
                    color = White
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        painter = painterResource(id = R.drawable.ic_wearable),
                        contentDescription = stringResource(id = R.string.wearable_question),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}