package rhea.group.app.ui.screens.video.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.components.CircularProgressBar
import rhea.group.app.components.LoadAsset
import rhea.group.app.components.PulsatingWidget
import rhea.group.app.components.frosted
import rhea.group.app.ui.theme.*
import rhea.group.app.utils.toMinutesAndSeconds

@Composable
fun CountDownBox(
    currentTimeInSeconds: Int,
    totalTimeInSeconds: Int,
    totalTimePercent: Float,
    heartRate: String
) {
    var currentTime =
        if (currentTimeInSeconds < 0) ":00" else currentTimeInSeconds.toMinutesAndSeconds()
    val totalTime =
        if (totalTimeInSeconds < 0) "00:00" else totalTimeInSeconds.toMinutesAndSeconds()
    if (currentTimeInSeconds < 60) {
        currentTime = if (currentTimeInSeconds < 10)
            ":0$currentTimeInSeconds"
        else
            ":$currentTimeInSeconds"
    }

    //FROSTED CONTAINER WITH TIMER
    Box(
        modifier = Modifier
            .fillMaxSize()
            .frosted(
                color = Mirage,
                alpha = 0.7f,
                borderRadius = 20.dp,
                shadowRadius = 30.dp,
                offsetX = 0.dp,
                offsetY = 0.dp
            )
            .padding(start = 10.dp, end = 5.dp, top = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .background(
                        shape = CircleShape,
                        color = Mirage
                    )
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
            VideoListTile(
                modifier = Modifier.padding(
                    start = 10.dp,
                    end = 20.dp,
                    top = 10.dp
                ),
                leading = {
                    CircularProgressBar(
                        modifier = Modifier.size(25.dp),
                        progress = totalTimePercent,
                        progressMax = 100f,
                        progressBarColor = Turquoise,
                        progressBarWidth = 4.dp,
                        backgroundProgressBarColor = SmokeyGrey,
                        backgroundProgressBarWidth = 4.dp,
                        roundBorder = true,
                        startAngle = 0f,
                        content = {}
                    )
                }, content = {
                    Text(
                        text = totalTime,
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 32.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                },
                trailing = {
                }
            )
            VideoListTile(
                modifier = Modifier.padding(
                    start = 10.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 20.dp
                ),
                leading = {
                    PulsatingWidget(
                        pulseFraction = 1.2f
                    ) {
                        LoadAsset(
                            modifier = Modifier
                                .size(25.dp)
                                .padding(start = 5.dp),
                            asset = R.drawable.ic_heart,
                            description = stringResource(id = R.string.target_heart_rate),
                            colorFilter = ColorFilter.tint(Persimmom)
                        )
                    }
                }, content = {
                    Text(
                        text = heartRate,
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 32.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                },
                trailing = {
                }
            )
        }

    }
    //END OF FROSTED CONTAINER WITH TIMER
}