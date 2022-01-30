package rhea.group.app.ui.screens.video.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rhea.group.app.R
import rhea.group.app.components.frosted
import rhea.group.app.ui.theme.Mirage
import rhea.group.app.ui.theme.Mirage_45
import rhea.group.app.ui.theme.White

@Composable
fun MainVideoControl(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    isPlayingLast: Boolean,
    isPreview: Boolean,
    onInfoClick: () -> Unit,
    onBackClick: () -> Unit,
    onBackLongClick: () -> Unit,
    onPlayClick: () -> Unit,
    onNextClick: () -> Unit,
    onTvClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
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
                    shape = CircleShape
                ),
            onClick = onInfoClick,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_information),
                contentDescription = "Info",
                modifier = Modifier.fillMaxSize(fraction = 0.07f)
            )
        }
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
                .wrapContentSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (!isPreview) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight(
                                fraction = 0.09f
                            )
                            .fillMaxWidth(
                                fraction = 0.20f
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = { onBackLongClick() },
                                    onTap = { onBackClick() }
                                )
                            },
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "Back"
                    )
                }
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
                if (!isPreview) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight(
                                fraction = 0.09f
                            )
                            .fillMaxWidth(
                                fraction = 0.20f
                            )
                            .clickable {
                                if (!isPlayingLast)
                                    onNextClick()
                            },
                        alpha = if (isPlayingLast) .4f else 1f,
                        painter = painterResource(R.drawable.ic_next),
                        contentDescription = "Next"
                    )
                }
            }
        }
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
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
                        shape = CircleShape
                    ),
                onClick = onTvClick,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_share_tv),
                    contentDescription = "Fullscreen",
                    modifier = Modifier.fillMaxSize(fraction = 0.07f)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            if (!isPreview) {
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
}