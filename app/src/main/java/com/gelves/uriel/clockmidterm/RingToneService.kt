package com.gelves.uriel.clockmidterm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.support.v4.app.NotificationCompat

/**
 * Created by Lucem on 11/02/2018.
 */
class RingToneService : Service() {

    var id: Int = 0
    var isRunning: Boolean = false

    companion object {
        lateinit var r: Ringtone
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var state:String = intent!!.getStringExtra("extra")
        assert(state!=null)
        when(state){
            "on" -> id = 1
            "off" -> id = 0
        }
        if (!this.isRunning && id == 1){
            playAlarm()
            this.isRunning = true
            this.id = 0
            fireNotif()}
        else if(this.isRunning && id == 0){
            r.stop()
            this.isRunning = false
            this.id = 0
        }
        else if(!this.isRunning && id == 0){
            this.isRunning = false
            this.id = 0
        }
        else if(this.isRunning && id == 1){
            this.isRunning = true
            this.id = 1
        }
        else{}

        return START_NOT_STICKY
    }

    private fun fireNotif() {
        var alarm_intent : Intent = Intent(this,Alarm::class.java)
        var pI : PendingIntent = PendingIntent.getActivity(this,0,alarm_intent,0)
        val defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var notifman: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notif : Notification = NotificationCompat.Builder(this).setContentTitle("Alarm is going off")
        .setSmallIcon(R.mipmap.ic_launcher).setSound(defSoundUri).setContentText("Click Me").setContentIntent(pI)
        .setAutoCancel(true).build()
        notifman.notify(0,notif)
    }

    private fun playAlarm() {
        var alarmUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        r = RingtoneManager.getRingtone(baseContext,alarmUri)
        r.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.isRunning = false
    }

}