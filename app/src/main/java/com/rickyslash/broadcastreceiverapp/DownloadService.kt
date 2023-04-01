package com.rickyslash.broadcastreceiverapp

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

// note: need to add <service/> in AndroidManifest.xml

class DownloadService: JobIntentService() {

    companion object {
        val TAG: String = DownloadService::class.java.simpleName
    }

    // it is being called when `startService(intent)` is called
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            // `this` refers to DownloadService object
            // `this::class.java` refers to the DownloadServiceClass
            enqueueWork(this, this::class.java, 101, intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    // this called when the intent is already passed to `enqueueWork()`
    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "Download Service is running")
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // it will send broadcast and notify the intent is finished with ACTION_DOWNLOAD_STATUS acts as a media
        val notifyFinishIntent = Intent(MainActivity.ACTION_DOWNLOAD_STATUS)
        sendBroadcast(notifyFinishIntent)
    }

}