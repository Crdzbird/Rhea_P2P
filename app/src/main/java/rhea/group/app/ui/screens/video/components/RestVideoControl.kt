package rhea.group.app.ui.screens.video.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.components.frosted
import rhea.group.app.models.Exercise
import rhea.group.app.ui.theme.Mirage
import rhea.group.app.ui.theme.Mirage_45
import rhea.group.app.ui.theme.White

@Composable
fun RestVideoControl(
    nextExercise: Exercise?,
    modifier: Modifier,
    isPlaying: Boolean,
    isPlayingLast: Boolean,
    onPlayClick: () -> Unit,
    onPlayLongClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier
                .size(40.dp)
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
            onClick = {}
        ) {
            Image(
                painter =
                if (isPlaying)
                    painterResource(R.drawable.ic_pause)
                else painterResource(R.drawable.ic_play),
                contentDescription = "Toggle Play",
                modifier = Modifier
                    .fillMaxSize(fraction = 0.6f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { onPlayLongClick() },
                            onTap = { onPlayClick() }
                        )
                    }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            if (!isPlayingLast) {
                Text(
                    text = nextExercise?.name ?: "",
                    modifier = Modifier
                        .width(150.dp)
                        .padding(start = 10.dp),
                    style = MaterialTheme.typography.body1.copy(
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Right
                    ),
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    modifier = Modifier
                        .size(40.dp)
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
                    onClick = {
                        if (!isPlayingLast)
                            onNextClick()
                    },
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_next),
                        contentDescription = "Next",
                        alpha = if (isPlayingLast) .4f else 1f,
                        modifier = Modifier
                            .fillMaxSize(fraction = 0.6f),
                        colorFilter = ColorFilter.tint(
                            color = White
                        )
                    )
                }
            }
        }
    }
}