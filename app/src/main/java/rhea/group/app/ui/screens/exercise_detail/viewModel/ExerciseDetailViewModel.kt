package rhea.group.app.ui.screens.exercise_detail.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.utils.exercises
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(private val navigator: ComposeNavigator) : ViewModel() {
    fun goBack() = navigator.navigateUp()
    fun navigateTo() = navigator.navigate(Screen.VideoScreen.createRoute(exercises[0].name, true))
}