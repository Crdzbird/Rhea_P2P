package rhea.group.app.ui.screens.session_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import rhea.group.app.ui.theme.White

@Composable
fun CardHitBox(
    modifier: Modifier = Modifier,
    leading: @Composable () -> Unit?,
    content: @Composable () -> Unit?,
    trailing: @Composable () -> Unit?,
    elevation: Dp? = 0.dp
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = White,
        contentColor = White,
        elevation = elevation ?: 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            leading() ?: Spacer(modifier = Modifier.size(0.dp))
            content() ?: Spacer(modifier = Modifier.size(0.dp))
            trailing() ?: Spacer(modifier = Modifier.size(0.dp))
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}