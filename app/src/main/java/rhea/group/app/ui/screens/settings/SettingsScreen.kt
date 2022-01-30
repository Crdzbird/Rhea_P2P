package rhea.group.app.ui.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import rhea.group.app.navigation.tabs.SettingsTabItem
import rhea.group.app.navigation.tabs.tabs
import rhea.group.app.ui.screens.settings.components.TabPage
import rhea.group.app.ui.screens.settings.components.TabTitleLayout
import rhea.group.app.ui.theme.Transparent

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SettingsScreen() {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                contentPadding = PaddingValues(top = 15.dp),
                elevation = 0.dp,
                backgroundColor = Transparent
            ) {
                TabTitleLayout(
                    tabs = tabs,
                    selectedIndex = pagerState.currentPage,
                    onPageSelected = { tabItem: SettingsTabItem ->
                        scope.launch {
                            pagerState.animateScrollToPage(tabItem.index)
                        }
                    }
                )
            }
        }
    ) {
        TabPage(tabItems = tabs, pagerState = pagerState)
    }
}