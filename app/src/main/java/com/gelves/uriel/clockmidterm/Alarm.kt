package com.gelves.uriel.clockmidterm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import java.util.*

class Alarm : AppCompatActivity() {

    var hour : Int = 0
    var min : Int = 0
    lateinit var pI : PendingIntent
    lateinit var am: AlarmManager
    lateinit var tp: TimePicker
    lateinit var updatetxt: TextView
    lateinit var con: Context
    lateinit var btnStart: Button
    lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        this.con = this
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        tp = findViewById(R.id.timePicker) as TimePicker
        updatetxt = findViewById(R.id.txtAlarm) as TextView
        btnStart = findViewById(R.id.btnAlarmW) as Button
        btnStop = findViewById(R.id.btnAlarmQ) as Button
        var calendar: Calendar = Calendar.getInstance()
        var myInt: Intent = Intent(this,AlarmReceiver ::class.java)
        btnStart.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(Calendar.HOUR_OF_DAY,tp.hour)
                    calendar.set(Calendar.MINUTE,tp.minute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = tp.hour
                    min = tp.minute
                }
                else{
                    calendar.set(Calendar.HOUR_OF_DAY,tp.currentHour)
                    calendar.set(Calendar.MINUTE,tp.currentMinute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = tp.currentHour
                    min = tp.currentMinute
                }
                var hr_str: String = hour.toString()
                var min_str: String = min.toString()
                if (hour > 12){
                    hr_str = (hour - 12).toString()
                }
                if (min < 10){
                    min_str = "0$min"
                }
                set_alarm_txt("Alarm set to: $hr_str : $min_str")
                myInt.putExtra("extra","on")
                pI = PendingIntent.getBroadcast(this@Alarm,0,myInt,PendingIntent.FLAG_UPDATE_CURRENT)
                am.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pI)
            }
        })
        btnStop.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                set_alarm_txt("Alarm off")
                pI = PendingIntent.getBroadcast(this@Alarm,0,myInt,PendingIntent.FLAG_UPDATE_CURRENT)
                am.cancel(pI)
                myInt.putExtra("extra","off")
                sendBroadcast(myInt)
            }
        })
    }

    private fun set_alarm_txt(s: String) {
        updatetxt.setText(s)
    }
}
