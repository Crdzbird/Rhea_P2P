package rhea.group.app.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import rhea.group.app.navigation.tabs.NavigationItem
import rhea.group.app.navigation.tabs.bottomItemStringResource
import rhea.group.app.ui.theme.Black
import rhea.group.app.ui.theme.LinkWater
import rhea.group.app.ui.theme.Turquoise
import rhea.group.app.ui.theme.White

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Stage,
        NavigationItem.Settings,
    )
    BottomNavigation(
        backgroundColor = White,
        contentColor = White,
        elevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = stringResource(id = bottomItemStringResource(item.route)),
                        softWrap = true,
                        style = MaterialTheme.typography.overline.copy(
                            color = if (currentRoute == item.route.name) Black else LinkWater
                        )
                    )
                },
                selectedContentColor = Turquoise,
                unselectedContentColor = LinkWater,
                alwaysShowLabel = true,
                selected = currentRoute == item.route.name,
                onClick = {
                    navController.navigate(item.route.name) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}