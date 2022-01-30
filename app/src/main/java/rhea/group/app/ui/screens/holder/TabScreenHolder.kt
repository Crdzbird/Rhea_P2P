package rhea.group.app.ui.screens.holder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import rhea.group.app.components.BottomNavigationBar
import rhea.group.app.navigation.tabs.TabsNavigation
import rhea.group.app.navigator.Navigator
import rhea.group.app.ui.screens.holder.viewModel.TabScreenHolderViewModel

@Composable
fun TabScreenHolder(
    tabScreenHolderViewModel: TabScreenHolderViewModel = hiltViewModel(),
    navigator: NavHostController
) {
    val navController = rememberNavController()
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(
                bottom = it.calculateBottomPadding()
            )
        ) {
            TabsNavigation(tabScreenHolderViewModel.navigator)
        }
    }
}