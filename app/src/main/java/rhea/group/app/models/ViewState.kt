package rhea.group.app.models

sealed class ViewState {
    object Unauthorized : ViewState()
    object Loading : ViewState()
    data class Success(val success: Any) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
