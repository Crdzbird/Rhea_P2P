package rhea.group.app.ui.screens.settings.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import rhea.group.app.navigation.tabs.SettingsTabItem
import rhea.group.app.navigation.tabs.tabItemStringResource
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.Botticelli

@Composable
fun TabTitleLayout(
    tabs: List<SettingsTabItem>,
    selectedIndex: Int,
    onPageSelected: ((tabItem: SettingsTabItem) -> Unit)
) {
    TabRow(
        selectedTabIndex = selectedIndex,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.background,
    ) {
        tabs.forEachIndexed { index, tabItem ->
            Tab(
                selected = index == selectedIndex,
                onClick = {
                    onPageSelected(tabItem)
                },
                text = {
                    Text(
                        text = stringResource(id = tabItemStringResource(tabItem.route)),
                        softWrap = true,
                        style = MaterialTheme.typography.button.copy(
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            textDecoration =
                            if (index == selectedIndex)
                                TextDecoration.Underline
                            else
                                null,
                            color = if (index == selectedIndex) Biscay else Botticelli
                        )
                    )
                }
            )
        }
    }
}