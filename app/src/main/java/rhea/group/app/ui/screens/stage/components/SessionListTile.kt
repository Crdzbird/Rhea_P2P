package rhea.group.app.ui.screens.stage.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.ui.theme.White

@Composable
fun SessionListTile(
    modifier: Modifier = Modifier,
    leading: @Composable () -> Unit?,
    content: @Composable () -> Unit?,
    trailing: @Composable () -> Unit?,
    isSelected: Boolean = false
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = if (isSelected) Turquoise else White,
        contentColor = if (isSelected) Turquoise else White,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leading() ?: Spacer(modifier = Modifier.size(0.dp))
            content() ?: Spacer(modifier = Modifier.size(0.dp))
            trailing() ?: Spacer(modifier = Modifier.size(0.dp))
        }
    }
}