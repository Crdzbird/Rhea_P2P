package rhea.group.app.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun LockScreenOrientation(
    orientation: Int,
    statusBarVisible: Boolean? = true,
    navigationBarVisible: Boolean? = true,
    systemBarVisible: Boolean? = true
) {
    val context = LocalContext.current
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = statusBarVisible ?: true
    systemUiController.isNavigationBarVisible = navigationBarVisible ?: true
    systemUiController.isSystemBarsVisible = systemBarVisible ?: true
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}