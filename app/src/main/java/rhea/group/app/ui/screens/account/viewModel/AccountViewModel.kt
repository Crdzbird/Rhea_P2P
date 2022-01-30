package rhea.group.app.ui.screens.account.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import rhea.group.app.models.Profile
import rhea.group.app.models.ViewState
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(val preferences: PrefsModeImpl) : ViewModel() {

    private val _accountViewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val accountViewState = _accountViewState.asStateFlow()
    lateinit var profile: Profile

    suspend fun initDataStore() {
        profile = preferences.profile.first()!!
        _accountViewState.value = ViewState.Success(success = profile)
    }

    suspend fun updateProfile(profile: Profile?) {
        profile?.let {
            preferences.updateProfile(profile = it)
        }
        _accountViewState.value = ViewState.Success(preferences.profile.first()!!)
    }
}