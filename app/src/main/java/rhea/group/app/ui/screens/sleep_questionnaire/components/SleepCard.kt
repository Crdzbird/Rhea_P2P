package rhea.group.app.ui.screens.sleep_questionnaire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rhea.group.app.R
import rhea.group.app.ui.theme.*

@Composable
fun SleepCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = "",
    elevation: Dp? = 0.dp,
    isPositive: Boolean?,
    onNegativeClick: () -> Unit = {},
    onPositiveClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = White,
        contentColor = White,
        elevation = elevation ?: 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1.copy(
                        color = Biscay,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                )
                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = subtitle,
                        style = MaterialTheme.typography.caption.copy(
                            color = Hoki,
                            fontFamily = FtpPolar,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clip(
                            shape = RoundedCornerShape(size = 33.dp),
                        )
                        .clickable {
                            onNegativeClick()
                        }
                        .background(
                            if (isPositive != null && !isPositive) {
                                Persimmom
                            } else {
                                LinkWater
                            }
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .align(
                                alignment = Alignment.Center
                            )
                            .padding(horizontal = 25.dp, vertical = 12.dp),
                        text = stringResource(id = R.string.no),
                        style = MaterialTheme.typography.button.copy(
                            color = if (isPositive != null && !isPositive) White else Hoki,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clip(
                            shape = RoundedCornerShape(size = 33.dp),
                        )
                        .clickable {
                            onPositiveClick()
                        }
                        .background(
                            if (isPositive != null && isPositive) {
                                Turquoise
                            } else {
                                LinkWater
                            }
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .align(
                                alignment = Alignment.Center
                            )
                            .padding(horizontal = 25.dp, vertical = 12.dp),
                        text = stringResource(id = R.string.yes),
                        style = MaterialTheme.typography.button.copy(
                            color = if (isPositive != null && isPositive) White else Hoki,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}