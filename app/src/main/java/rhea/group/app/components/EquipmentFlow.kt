package rhea.group.app.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import rhea.group.app.R
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.WhiteLilac

@Composable
fun EquipmentFlow(
    modifier: Modifier = Modifier,
    background: Color,
    equipments: List<String>?
) {
    Text(
        text = stringResource(id = R.string.equipment_required).uppercase(),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.body2.copy(
            textAlign = TextAlign.Center,
            color = Biscay,
            textDecoration = TextDecoration.Underline
        ),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(12.dp))
    FlowRow(
        modifier = modifier,
        verticalGap = 8.dp,
        horizontalGap = 8.dp,
        alignment = Alignment.CenterHorizontally,
    ) {
        equipments?.let {
            it.map { equipment ->
                Badge(
                    modifier = Modifier.clip(
                        RoundedCornerShape(20.dp)
                    ),
                    backgroundColor = background
                ) {
                    Text(
                        modifier = Modifier.padding(
                            horizontal = 35.dp,
                            vertical = 17.dp
                        ),
                        text = equipment,
                        style = MaterialTheme.typography.button.copy(
                            color = Biscay
                        )
                    )
                }
            }
        } ?: run {
            Badge(
                modifier = Modifier.clip(
                    RoundedCornerShape(20.dp)
                ),
                backgroundColor = WhiteLilac
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 35.dp,
                        vertical = 17.dp
                    ),
                    text = stringResource(id = R.string.none),
                    style = MaterialTheme.typography.button.copy(
                        color = Biscay
                    )
                )
            }
        }
    }
}