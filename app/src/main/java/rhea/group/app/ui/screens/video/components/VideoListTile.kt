package rhea.group.app.ui.screens.video.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rhea.group.app.ui.theme.Transparent

@Composable
fun VideoListTile(
    modifier: Modifier = Modifier,
    leading: @Composable () -> Unit?,
    content: @Composable () -> Unit?,
    trailing: @Composable () -> Unit?
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Transparent,
        contentColor = Transparent,
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