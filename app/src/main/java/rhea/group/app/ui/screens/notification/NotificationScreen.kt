package rhea.group.app.ui.screens.notification

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rhea.group.app.R
import rhea.group.app.ui.screens.notification.components.SwitchTile

@Composable
fun NotificationScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                start = 25.dp,
                end = 25.dp,
                top = 38.0.dp
            ),
    ) {
        SwitchTile(
            title = stringResource(id = R.string.plan_progress_update),
            subtitle = stringResource(id = R.string.lorem),
            checked = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        SwitchTile(
            title = stringResource(id = R.string.sleep_hygiene_questionnaire),
            subtitle = stringResource(id = R.string.lorem),
            checked = false
        )
        Spacer(modifier = Modifier.height(40.dp))
        SwitchTile(
            title = stringResource(id = R.string.promotional_marketing),
            subtitle = stringResource(id = R.string.lorem),
            checked = false
        )
        Spacer(modifier = Modifier.height(40.dp))
        SwitchTile(
            title = stringResource(id = R.string.product_and_update),
            subtitle = stringResource(id = R.string.lorem),
            checked = false
        )
    }
}