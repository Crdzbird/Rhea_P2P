package rhea.group.app

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.net.*
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.wear.ambient.AmbientModeSupport
import androidx.wear.phone.interactions.PhoneTypeHelper
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Wearable
import group.rhea.shared.UPDATE_HR_ACTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import rhea.group.app.databinding.ActivityMainBinding
import rhea.group.app.service.MessageHRService

class MainActivity : FragmentActivity(), AmbientModeSupport.AmbientCallbackProvider {
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var binding: ActivityMainBinding

    private lateinit var ac: AmbientModeSupport.AmbientController
    private lateinit var cm: ConnectivityManager
    private lateinit var cc: CapabilityClient

    private val intentFilter = IntentFilter(UPDATE_HR_ACTION)
    private var standalone: Boolean = false

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (!ac.isAmbient) {
                val hr: Any = intent?.extras?.get("bpm") ?: return
                val accuracy: Int = intent.extras?.get("accuracy") as Int

                binding.textHR.text = "$hr"
                binding.textAccuracy.text = getString(
                    R.string.template_accuracy,
                    resources.getStringArray(R.array.accuracy_levels)[accuracy + 1]
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Managers
        ac = AmbientModeSupport.attach(this)    // Enable Always-on
        cc = Wearable.getCapabilityClient(this)
        cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        // Request Permission
        if (checkSelfPermission(android.Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(android.Manifest.permission.BODY_SENSORS), 100)
        }

        ////Zero out HR
        binding.textAccuracy.text = getString(R.string.template_accuracy, "")
        binding.textHR.text = "000"
    }

    override fun onStart() {
        super.onStart()

        registerReceiver(broadcastReceiver, intentFilter)

        // Check Permission Before Starting Service
        if (checkSelfPermission(android.Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
            chooseHRService()
        }
    }

    override fun onResume() {
        super.onResume()

        //Battery
        updateBattery()
    }

    override fun onStop() {
        super.onStop()
        try {
            unregisterReceiver(broadcastReceiver)
        } catch (e: IllegalArgumentException) {
            //Log.w(TAG, "BroadcastReciever was somehow not registered")
            e.printStackTrace()
        }
        Toast.makeText(this, getString(R.string.text_background_streaming), Toast.LENGTH_LONG)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                chooseHRService()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.error_heartrate_permission),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {
        return object : AmbientModeSupport.AmbientCallback() {
            override fun onEnterAmbient(ambientDetails: Bundle) {
                binding.root.visibility = View.GONE
                try {
                    unregisterReceiver(broadcastReceiver)
                } catch (e: IllegalArgumentException) {
                    //Log.w(TAG, "Illegal Argument when unregistering broadcastReceiver")
                    e.printStackTrace()
                }
            }

            override fun onExitAmbient() {
                registerReceiver(broadcastReceiver, intentFilter)
                binding.root.visibility = View.VISIBLE
                updateBattery()
            }
        }
    }

    //Helpers
    private fun chooseHRService() {
        if (!isHRServiceRunning()) {
            val isPhoneAndroid =
                PhoneTypeHelper.getPhoneDeviceType(this) == PhoneTypeHelper.DEVICE_TYPE_ANDROID
            if (isPhoneAndroid) {
                if (Build.VERSION.SDK_INT >= 26) {
                    //Phone is Android, Wear can run standalone or companion
                    //Give user choice
                    startHRService()
                } else {
                    //Phone is Android, Wear can't run standalone, can run companion
                    startHRService()
                }
            } else {
                if (Build.VERSION.SDK_INT >= 26) {
                    //Phone is iOS or other, Wear can run standalone, can't run companion
                    startHRService()
                } else {
                    //Phone is iOS or other, Wear can't run standalone or companion
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.alert_title_error_ios_too_old))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage(getString(R.string.error_ios_too_old))
                        .setPositiveButton(getString(R.string.button_ok)) { _, _ ->
                            finish()
                        }
                        .create().show()
                }
            }
        }
    }

    private fun startHRService() {
        Toast.makeText(this, R.string.text_starting_companion, Toast.LENGTH_LONG).show()
        Intent(this, MessageHRService::class.java).also { intent ->
            startService(intent)
        }
        launchPhoneApp()
    }


    private fun updateBattery() {
        val bm = (getSystemService(BATTERY_SERVICE) as BatteryManager)
        val batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        binding.textBattery?.text = "$batteryLevel%"
        binding.textBatteryRound?.text = "$batteryLevel%"
        if (bm.isCharging) {
            binding.imageBattery.setImageDrawable(getDrawable(R.drawable.battery_charge))
        } else {
            binding.imageBattery.setImageDrawable(getDrawable(R.drawable.battery))
        }
        binding.imageBattery.setImageLevel(batteryLevel)
    }

    private fun isHRServiceRunning(): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            val serviceName = service.service.className
            if (MessageHRService::class.java.name == serviceName) {
                return true
            }
        }
        return false
    }

    private fun launchPhoneApp() {
        scope.launch {
            val capabilityInfo: CapabilityInfo = Tasks.await(
                cc.getCapability(
                    getString(R.string.capability_phone),
                    CapabilityClient.FILTER_REACHABLE
                )
            )
            if (capabilityInfo.nodes.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, R.string.app_not_found, Toast.LENGTH_LONG)
                        .show()
                }
                val connectedNodes =
                    Tasks.await(Wearable.getNodeClient(this@MainActivity).connectedNodes)
                if (connectedNodes.isEmpty()) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.no_devices_found,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    try {
                        RemoteActivityHelper(this@MainActivity).startRemoteActivity(
                            Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse("market://details?id=rhea.group.app"))
                                .addCategory(Intent.CATEGORY_BROWSABLE)
                        )
                            .await()
                    } catch (e: Exception) {
                        println("Exception while launching Play Store on Phone")
                        e.printStackTrace()
                    }
                }
            } else {
                try {
                    RemoteActivityHelper(this@MainActivity).startRemoteActivity(
                        Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse("app://rhea.group.app"))
                            .addCategory(Intent.CATEGORY_BROWSABLE)
                    )
                        .await()
                } catch (e: Exception) {
                    println("Exception while launching Phone app")
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}