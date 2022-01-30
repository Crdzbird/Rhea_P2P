package rhea.group.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import rhea.group.app.R
import rhea.group.app.models.ViewState
import rhea.group.app.ui.theme.Biscay
import rhea.group.app.ui.theme.Botticelli
import rhea.group.app.ui.theme.FtpPolar
import rhea.group.app.ui.theme.White
import rhea.group.app.wearable.WearableViewModel

@Composable
fun WearableSelector(
    header: String,
    explanation: String,
    bottom: String,
    wearableViewModel: WearableViewModel = hiltViewModel()
) {
    val scope = CoroutineScope(Dispatchers.IO)
    val isRefreshingState = wearableViewModel.isRefreshingState.collectAsState().value
    LaunchedEffect(key1 = header) {
        wearableViewModel.init(startReceiver = false)
        wearableViewModel.fetchAvailableDevices(scope)
    }
    val wearableViewState = wearableViewModel.wearableViewState.collectAsState().value
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshingState),
        onRefresh = {
            wearableViewModel.onRefresh(scope)
        }
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = header,
                style = MaterialTheme.typography.h3.copy(
                    color = Biscay
                )
            )
            Text(
                text = explanation,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontFamily = FtpPolar,
                    color = Biscay
                )
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                when (wearableViewState) {
                    is ViewState.Success -> {
                        if (wearableViewModel.devices.isEmpty()){
                            item {
                                WearableCard(
                                    background = White,
                                    icon = R.drawable.ic_android_watcher,
                                    text = stringResource(id = R.string.empty),
                                    textColor = Biscay,
                                    colorFilter = null,
                                    onClick = {}
                                )
                            }
                        }else{
                            items(wearableViewModel.devices) {
                                WearableCard(
                                    background = if (it.id == wearableViewModel.selectedId)
                                        Biscay
                                    else
                                        White,
                                    icon = R.drawable.ic_android_watcher,
                                    colorFilter = if (it.id == wearableViewModel.selectedId) {
                                        ColorFilter.tint(
                                            color = White
                                        )
                                    } else {
                                        null
                                    },
                                    text = it.displayName,
                                    textColor = if (it.id == wearableViewModel.selectedId)
                                        White
                                    else
                                        Biscay,
                                    onClick = {
                                        wearableViewModel.setWearable(it.id)
                                    }
                                )
                            }
                        }
                    }
                    else -> {
                        items(2) {
                            WearableCard(
                                background = White,
                                icon = R.drawable.ic_android_watcher,
                                text = stringResource(id = R.string.rhea),
                                textColor = Biscay,
                                colorFilter = null,
                                onClick = {}
                            )
                        }
                    }
                }
            }
            Text(
                text = bottom,
                style = MaterialTheme.typography.body2.copy(
                    textAlign = TextAlign.Center,
                    color = Botticelli
                )
            )
        }
    }
}