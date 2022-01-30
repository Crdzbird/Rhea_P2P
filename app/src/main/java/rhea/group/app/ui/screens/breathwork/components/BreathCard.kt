package rhea.group.app.ui.screens.breathwork.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.components.LoadAsset
import rhea.group.app.models.BreathworkOption
import rhea.group.app.ui.theme.Hoki
import rhea.group.app.ui.theme.LinkWater
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.ui.theme.White

@Composable
fun BreathCard(
    breathworkOption: BreathworkOption,
    elevation: Dp? = 0.dp,
    onTap: () -> Unit = {},
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable {
                onTap()
            },
        shape = RoundedCornerShape(20.dp),
        backgroundColor = White,
        contentColor = White,
        elevation = elevation ?: 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = breathworkOption.name,
                    style = MaterialTheme.typography.h6.copy(
                        color = Turquoise,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(fraction = .8f),
                        text = breathworkOption.description,
                        style = MaterialTheme.typography.caption.copy(
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = Hoki
                        )
                    )
                    LoadAsset(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 9.dp, bottom = 9.dp),
                        asset = R.drawable.ic_play,
                        description = stringResource(id = R.string.load),
                        colorFilter = ColorFilter.tint(LinkWater)
                    )
                }
            }
        }
    }
}