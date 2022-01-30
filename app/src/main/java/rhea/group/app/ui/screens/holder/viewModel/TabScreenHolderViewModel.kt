package rhea.group.app.ui.screens.holder.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rhea.group.app.navigator.ComposeNavigator
import javax.inject.Inject

@HiltViewModel
class TabScreenHolderViewModel @Inject constructor(val navigator: ComposeNavigator) : ViewModel()