package rhea.group.app.wearable


import android.content.Intent
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import group.rhea.shared.UPDATE_HR_ACTION
import group.rhea.shared.service.BaseService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class Request(val bpm: Int)

data class HeartRateData(val heartRate: Int, val timeStamp: Long)

class HeartRateService : BaseService() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val heartRateMap = mutableMapOf<String, HeartRateData>()

    private val messageListener = MessageClient.OnMessageReceivedListener { messageEvent ->
        if (messageEvent.path == MESSAGE_PATH) {
            //Log.i(TAG, "Received Message")
            updateHRMap(messageEvent.sourceNodeId, messageEvent.data.decodeToString().toInt())
            broadcastHR()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        //Start Wear Service
        Wearable.getMessageClient(this).addListener(messageListener)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        //Stop Wear Service
        Wearable.getMessageClient(this).removeListener(messageListener)
    }

    //Helpers
    override fun broadcastHR() {
        val updateHRIntent = Intent(UPDATE_HR_ACTION)
        updateHRIntent.putExtra("bpm", heartRate)
        this.sendBroadcast(updateHRIntent)
    }

    private fun updateHRMap(senderID: String, heartRate: Int) {
        //Add HR to Map
        heartRateMap[senderID] = HeartRateData(heartRate, System.currentTimeMillis())

        //Remove old HRs from map
        for (key in heartRateMap.keys) {
            val currentTimeStamp = System.currentTimeMillis()
            if (currentTimeStamp - heartRateMap[key]!!.timeStamp >= 60000) {
                heartRateMap.remove(key)
            }
        }

        //Calculate Average HR
        var heartRateTotal = 0
        for (hrd in heartRateMap.values) {
            heartRateTotal += hrd.heartRate
        }
        this.heartRate = heartRateTotal / heartRateMap.size
    }

    companion object {
        private const val TAG = "PhoneHRService"
        private const val MESSAGE_PATH = "/heart_streamer_phone"
    }
}