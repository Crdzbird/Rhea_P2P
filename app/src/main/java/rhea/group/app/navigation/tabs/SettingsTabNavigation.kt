package rhea.group.app.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import rhea.group.app.R
import rhea.group.app.components.WearableSelector
import rhea.group.app.ui.screens.account.AccountScreen
import rhea.group.app.ui.screens.notification.NotificationScreen

val tabs = listOf(
    SettingsTabItem.Account,
    SettingsTabItem.Notifications,
    SettingsTabItem.Devices
)

sealed class SettingsTabItem(
    val index: Int,
    val icon: ImageVector,
    val title: String,
    val route: TabItem,
    val screenToLoad: @Composable () -> Unit
) {
    object Account : SettingsTabItem(
        0,
        Icons.Default.AccountCircle,
        "Account",
        TabItem.Account,
        { AccountScreen(accountViewModel = hiltViewModel()) })

    object Notifications : SettingsTabItem(
        1,
        Icons.Default.Notifications,
        "Notifications",
        TabItem.Notifications,
        { NotificationScreen() })

    object Devices :
        SettingsTabItem(
            2,
            Icons.Default.Phone,
            "Devices",
            TabItem.Devices,
            {
                WearableSelector(
                    header = stringResource(id = R.string.connect_wearable),
                    explanation = stringResource(id = R.string.connect_wearable_description),
                    bottom = ""
                )
            }
        )
}

enum class TabItem {
    Account,
    Notifications,
    Devices
}


fun tabItemStringResource(tabItem: TabItem): Int {
    return when (tabItem) {
        TabItem.Account -> R.string.account
        TabItem.Notifications -> R.string.notifications
        TabItem.Devices -> R.string.devices
    }
}