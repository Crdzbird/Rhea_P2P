package rhea.group.app.ui.screens.video.breath.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rhea.group.app.R
import rhea.group.app.components.frosted
import rhea.group.app.ui.theme.Mirage
import rhea.group.app.ui.theme.Mirage_45
import rhea.group.app.ui.theme.White

@Composable
fun BreathVideoControl(
    modifier: Modifier,
    isPlaying: Boolean,
    onPlayClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
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
                .background(
                    color = Mirage_45,
                    shape = RoundedCornerShape(50.dp)
                )
                .align(
                    alignment = Alignment.Center
                )
                .wrapContentSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .wrapContentSize()
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(
                            fraction = 0.09f
                        )
                        .clickable {
                            onPlayClick()
                        },
                    painter =
                    if (isPlaying)
                        painterResource(R.drawable.ic_pause)
                    else painterResource(R.drawable.ic_play),
                    contentDescription = "Toggle Play"
                )
            }
        }
        Box(
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterEnd
                )
                .wrapContentSize(),
        ) {
            IconButton(
                modifier = Modifier
                    .wrapContentSize()
                    .frosted(
                        color = Mirage,
                        alpha = 0.7f,
                        borderRadius = 20.dp,
                        shadowRadius = 30.dp,
                        offsetX = 0.dp,
                        offsetY = 0.dp
                    )
                    .background(
                        color = Mirage_45,
                        shape = CircleShape
                    ),
                onClick = onCloseClick,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "Close",
                    modifier = Modifier
                        .fillMaxSize(fraction = 0.07f),
                    colorFilter = ColorFilter.tint(
                        color = White
                    )
                )
            }
        }
    }
}