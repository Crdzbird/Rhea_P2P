package rhea.group.app.ui.screens.notification.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.ui.theme.*

@Composable
fun SwitchTile(
    title: String,
    subtitle: String,
    checked: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.widthIn(min = 1.dp, max = 175.dp),
                text = title,
                style = MaterialTheme.typography.button.copy(
                    textAlign = TextAlign.Start,
                    color = Biscay
                )
            )
            Text(
                modifier = Modifier.widthIn(min = 1.dp, max = 250.dp),
                text = subtitle,
                style = MaterialTheme.typography.overline.copy(
                    fontFamily = FtpPolar,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    color = Hoki
                )
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Switch(
            checked = checked,
            onCheckedChange = {},
            colors = SwitchDefaults.colors(
                checkedThumbColor = Turquoise,
                checkedTrackColor = White,
                uncheckedThumbColor = Botticelli,
                uncheckedTrackColor = White
            )
        )
    }
}