package rhea.group.app.models

import androidx.compose.runtime.Composable

enum class NetworkState {
    IDLE,
    LOADING,
    ERROR,
    SUCCESS
}

@Composable
fun NetworkState.onSuccess(block: @Composable () -> Unit): NetworkState {
    if (this == NetworkState.SUCCESS) {
        block()
    }
    return this
}

@Composable
fun NetworkState.onLoading(block: @Composable () -> Unit): NetworkState {
    if (this == NetworkState.LOADING) {
        block()
    }
    return this
}

@Composable
fun NetworkState.onError(block: @Composable () -> Unit): NetworkState {
    if (this == NetworkState.ERROR) {
        block()
    }
    return this
}
