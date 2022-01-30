package rhea.group.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    background = WhiteLilac,
    primary = AlbescentWhite,
    primaryVariant = White,
    secondary = BlackSqueeze,
    secondaryVariant = Turquoise
)

private val LightColorPalette = lightColors(
    background = WhiteLilac,
    primary = AlbescentWhite,
    primaryVariant = White,
    secondary = BlackSqueeze,
    secondaryVariant = Turquoise
)

@Composable
fun RheaAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Transparent, darkIcons = false)
    var colors = LightColorPalette
    if (darkTheme) {
        colors = DarkColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}