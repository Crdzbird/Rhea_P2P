package rhea.group.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import rhea.group.app.navigation.authenticated.AuthenticatedNavigation
import rhea.group.app.navigation.freemium.FreemiumNavigation
import rhea.group.app.navigation.unauthenticated.UnauthenticatedNavigation
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.ui.theme.RheaAppTheme
import rhea.group.app.worker.WorkerViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: ComposeNavigator
    private val workerViewModel: WorkerViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainActivityViewModel.isLoading.value
            }
        }
        setContent {
            val mainActivityState = mainActivityViewModel.userStatus.collectAsState().value
            RheaAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    when (mainActivityState) {
                        UserStatusEnum.Unauthenticated -> UnauthenticatedNavigation(navigator)
                        UserStatusEnum.Paid -> AuthenticatedNavigation(navigator)
                        UserStatusEnum.Free -> FreemiumNavigation(navigator)
                        UserStatusEnum.Offline -> AuthenticatedNavigation(navigator)
                    }
                }
            }
        }
        workerViewModel.startWork()
    }
}