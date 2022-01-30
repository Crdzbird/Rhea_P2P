package rhea.group.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WearableCard(
    background: Color,
    icon: Int,
    colorFilter: ColorFilter?,
    text: String,
    textColor: Color,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(165.dp)
            .padding(horizontal = 6.dp)
            .clip(
                shape = RoundedCornerShape(size = 20.dp),
            )
            .clickable {
                onClick()
            }
            .background(
                color = background
            )
    ) {
        Column(
            modifier = Modifier.align(
                alignment = Alignment.Center
            ),
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit,
                colorFilter = colorFilter
            )
            Text(
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                        end = 5.dp,
                        top = 13.dp,
                        bottom = 5.dp
                    )
                    .align(
                        Alignment.CenterHorizontally
                    ),
                text = text,
                style = MaterialTheme.typography.body2.copy(
                    color = textColor,
                    textAlign = TextAlign.Center
                ),
                softWrap = true
            )
        }
    }
}