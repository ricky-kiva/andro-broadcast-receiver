package com.rickyslash.broadcastreceiverapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import com.rickyslash.broadcastreceiverapp.databinding.ActivityMainBinding
import android.Manifest

// note: need to add <uses-permission> & <receiver> inside the AndroidManifest.xml

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivityMainBinding? = null

    // function for requesting permission for reading SMS
    var requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "SMS Receiver Permission accepted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "SMS Receiver Permission declined", Toast.LENGTH_SHORT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnPermission?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_permission -> requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

/* BACKGROUND TASK */
// - Broadcast Receiver: way to subscribe & listen to an event, and do some action (using publish-subscribe pattern)
// --- infographic: https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:9ff60cdeb50d4ea62686901d22cf31e420220110202911.jpeg
// - Notification: way to notify user (pop-up, status bar notification, etc)
// - AlarmManager: way to do time-based operation outside the lifecycle of the app
// - WorkManager: deferrable background process that always going to be executed though the app is closed / device restart (new in jetpack)
// --- example: do something when there is internet connection / device is charged

// Broadcast message: the message that is being broadcast-ed by Intent object

// 2 type of Broadcast message:
// - System Broadcast: message that's being sent by system (when plugging in headset, connected to internet, on plane-mode, etc)
// - Custom Broadcast: message that is custom-ly made by app (when download is finished, etc)

// 2 ways to register app to receive Broadcast Message:
// - Static Receiver: assigned in AndroidManifest.xml using <intent-filters> tag (also called Manifest-declared receivers) (pretty much deprecated on API level 26 (Android 8))
// - Dynamic Receivers: assigned in Activity using context.registerReceiver (also called Context-registered receivers)