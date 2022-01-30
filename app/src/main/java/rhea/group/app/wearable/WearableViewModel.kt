package rhea.group.app.wearable

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.wear.remote.interactions.RemoteActivityHelper
import androidx.work.await
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.lifecycle.HiltViewModel
import group.rhea.shared.UPDATE_HR_ACTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import rhea.group.app.models.ViewState
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Inject

@HiltViewModel
class WearableViewModel @Inject constructor(
    application: Application,
    val preferences: PrefsModeImpl,
) : AndroidViewModel(application) {

    private lateinit var cc: CapabilityClient
    private val _wearableViewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val wearableViewState = _wearableViewState.asStateFlow()
    private val _heartRateState = MutableStateFlow<Any?>(null)
    val heartRateState = _heartRateState.asStateFlow()
    private val _isRefreshingState = MutableStateFlow(false)
    val isRefreshingState = _isRefreshingState.asStateFlow()
    var devices: MutableList<Node> = mutableListOf()
    var selectedId: String = ""
    private val context
        get() = getApplication<Application>()

    private val intentFilter = IntentFilter(UPDATE_HR_ACTION)

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            runBlocking {
                preferences.updateBpm(
                    Integer.parseInt(
                        intent?.extras?.get("bpm").toString()
                    )
                )
            }
            val bpm = runBlocking { preferences.bpm.first() }
            val hr: Any = intent?.extras?.get("bpm") ?: bpm
            _heartRateState.value = hr
        }
    }


    fun onRefresh(scope: CoroutineScope) {
        fetchAvailableDevices(scope)
        _isRefreshingState.value = false
    }

    private fun startReceiver() {
        context.registerReceiver(broadcastReceiver, intentFilter)
        Intent(context, HeartRateService::class.java).also { context.startService(it) }
    }

    private fun stopReceiver() {
        context.unregisterReceiver(broadcastReceiver)
    }

    override fun onCleared() {
        stopReceiver()
        super.onCleared()
    }

    fun init(startReceiver: Boolean = true) {
        val bpm = runBlocking { preferences.bpm.first() }
        selectedId = runBlocking { preferences.wearableId.first() ?: "" }
        _heartRateState.value = bpm
        cc = Wearable.getCapabilityClient(context)
        if (startReceiver) {
            startReceiver()
        }
    }

    fun setWearable(id: String) {
        _wearableViewState.value = ViewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            preferences.updateWearableId(id)
            selectedId = id
            _wearableViewState.value = ViewState.Success(success = devices)
        }
    }

    fun connectToDevice(scope: CoroutineScope) {
        scope.launch {
            try {
                RemoteActivityHelper(context).startRemoteActivity(
                    Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("app://rhea.group.app"))
                        .addCategory(Intent.CATEGORY_BROWSABLE), selectedId
                ).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchAvailableDevices(scope: CoroutineScope) {
        _wearableViewState.value = ViewState.Loading
        scope.launch {
            val capabilityInfo: CapabilityInfo = Tasks.await(
                cc.getCapability(
                    "heart_streamer_wear",
                    CapabilityClient.FILTER_REACHABLE
                )
            )
            if (capabilityInfo.nodes.isEmpty()) {
                val connectedNodes = Tasks.await(Wearable.getNodeClient(context).connectedNodes)
                if (connectedNodes.isNotEmpty()) {
                    devices = connectedNodes
                }
            } else {
                devices = capabilityInfo.nodes.toMutableList()
            }
            _wearableViewState.value = ViewState.Success(success = devices)
        }
    }

    fun checkWearable(scope: CoroutineScope, shouldRun: Boolean = true) {
        scope.launch {
            if (selectedId.isNotEmpty()) {
                val capabilityInfo: CapabilityInfo = Tasks.await(
                    cc.getCapability(
                        "heart_streamer_wear",
                        CapabilityClient.FILTER_REACHABLE
                    )
                )
                if (capabilityInfo.nodes.isEmpty()) {
                    val connectedNodes = Tasks.await(Wearable.getNodeClient(context).connectedNodes)
                    if (connectedNodes.isNotEmpty()) {
                        checkMarket()
                    } else {
                        if (shouldRun) {
                            connectToDevice(scope)
                        }
                    }
                }
            }
        }
    }

    fun checkMarket() {
        try {
            RemoteActivityHelper(context).startRemoteActivity(
                Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("market://details?id=rhea.group.app"))
                    .addCategory(Intent.CATEGORY_BROWSABLE), selectedId
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}