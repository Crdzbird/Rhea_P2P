package rhea.group.app.ui.screens.video.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.ui.theme.Mirage
import rhea.group.app.ui.theme.Portica
import rhea.group.app.utils.toMinutesAndSeconds

@Composable
fun RestCountDownBox(
    currentTimeInSeconds: Int,
) {
    val currentTime =
        if (currentTimeInSeconds < 0 || currentTimeInSeconds > 1000) "00:00"
        else currentTimeInSeconds.toMinutesAndSeconds()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 5.dp, top = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = R.string.rest),
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.h2.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = stringResource(id = R.string.rest_description),
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.body1.copy(
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.size(35.dp))
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .background(
                        shape = CircleShape,
                        color = Mirage
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentTime,
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                        vertical = 20.dp
                    ),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        lineHeight = 50.sp,
                        textAlign = TextAlign.Center,
                        color = Portica
                    ),
                    softWrap = true
                )
            }
        }

    }
}