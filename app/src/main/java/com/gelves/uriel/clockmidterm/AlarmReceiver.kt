package com.gelves.uriel.clockmidterm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Lucem on 11/02/2018.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        var getResult: String = intent!!.getStringExtra("extra")

        var service_int: Intent = Intent(context,RingToneService::class.java)
        service_int.putExtra("extra",getResult)
        context!!.startService(service_int)
    }
}