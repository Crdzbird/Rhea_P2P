package rhea.group.app.ui.screens.authentication.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    preferences: PrefsModeImpl,
    private val navigator: ComposeNavigator
) : ViewModel() {

    fun navigateToCredentials() {
        navigator.navigate(Screen.EmailAuthScreen.route)
    }

    val profile = preferences.profile

    init {
        runBlocking {
            profile.first()?.let {
                if (it.trialStatus == "paid") {
                    navigator.navigate(Screen.TabHolderScreen.route) {
                        popUpTo(Screen.AuthScreen.route) { inclusive = true }
                    }
                } else {
                    navigator.navigate(Screen.TrialScreen.route) {
                        popUpTo(Screen.AuthScreen.route) { inclusive = true }
                    }
                }
            }
        }
    }
}