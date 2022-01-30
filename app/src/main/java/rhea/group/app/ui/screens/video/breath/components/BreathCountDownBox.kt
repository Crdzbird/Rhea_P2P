package rhea.group.app.ui.screens.video.breath.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.components.frosted
import rhea.group.app.ui.theme.BlackSqueeze
import rhea.group.app.ui.theme.Portica
import rhea.group.app.utils.toSeconds

@Composable
fun BreathCountDownBox(
    modifier: Modifier = Modifier,
    currentTimeInSeconds: Int,
    title: String,
) {
    val currentTime = currentTimeInSeconds.toSeconds()
    Box(
        modifier = modifier
            .frosted(
                color = BlackSqueeze,
                alpha = 0.5f,
                borderRadius = 33.dp,
                shadowRadius = 1.dp,
                offsetX = 0.dp,
                offsetY = 0.dp
            )
            .padding(horizontal = 15.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.h1.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 38.sp,
                    fontSize = 60.sp,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = stringResource(id = R.string.hold_for),
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = currentTime,
                modifier = Modifier.padding(
                    horizontal = 15.dp,
                    vertical = 20.dp
                ),
                style = MaterialTheme.typography.h1.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 38.sp,
                    fontSize = 60.sp,
                    textAlign = TextAlign.Center,
                    color = Portica
                ),
                softWrap = true
            )
        }

    }
}