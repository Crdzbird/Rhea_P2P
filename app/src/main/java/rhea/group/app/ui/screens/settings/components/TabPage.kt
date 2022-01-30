package rhea.group.app.ui.screens.settings.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import rhea.group.app.navigation.tabs.SettingsTabItem
import rhea.group.app.navigation.tabs.tabs

@ExperimentalPagerApi
@Composable
fun TabPage(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabItems: List<SettingsTabItem>
) {
    HorizontalPager(
        modifier = modifier,
        count = tabs.size,
        state = pagerState
    ) { index ->
        tabItems[index].screenToLoad()
    }
}