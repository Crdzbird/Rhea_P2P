package rhea.group.app.navigation.tabs

import rhea.group.app.R

sealed class NavigationItem(var route: BottomItem, var icon: Int, var title: String) {
    object Stage : NavigationItem(BottomItem.Stage, R.drawable.ic_stage, "Stage")
    object Settings : NavigationItem(BottomItem.Settings, R.drawable.ic_settings, "Settings")
}

enum class BottomItem {
    Stage,
    Settings
}


fun bottomItemStringResource(bottomItem: BottomItem): Int {
    return when (bottomItem) {
        BottomItem.Stage -> R.string.stage
        BottomItem.Settings -> R.string.settings
    }
}