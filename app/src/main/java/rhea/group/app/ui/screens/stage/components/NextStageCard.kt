package rhea.group.app.ui.screens.stage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NextStageCard(
    modifier: Modifier = Modifier,
    leading: @Composable () -> Unit?,
    content: @Composable () -> Unit?,
) {
    Box(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 40.dp,
                    vertical = 15.dp
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leading()
            content()
        }
    }
}