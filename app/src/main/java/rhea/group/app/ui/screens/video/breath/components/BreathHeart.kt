package rhea.group.app.ui.screens.video.breath.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.components.LoadAsset
import rhea.group.app.components.PulsatingWidget
import rhea.group.app.components.frosted
import rhea.group.app.ui.screens.video.components.VideoListTile
import rhea.group.app.ui.theme.Mirage
import rhea.group.app.ui.theme.Persimmom

@Composable
fun BreathHeart(
    heartRate: String
) {
    Box(
        modifier = Modifier
            .frosted(
                color = Mirage,
                alpha = 0.7f,
                borderRadius = 20.dp,
                shadowRadius = 30.dp,
                offsetX = 0.dp,
                offsetY = 0.dp
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        VideoListTile(
            modifier = Modifier
                .padding(
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
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