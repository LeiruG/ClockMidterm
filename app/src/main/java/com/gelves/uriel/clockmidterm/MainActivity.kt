package com.gelves.uriel.clockmidterm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAlarm.setOnClickListener{
            val iA = Intent(this, Alarm:: class.java)
            startActivity(iA)
        }

        btnStopwatch.setOnClickListener{
            val iB = Intent(this, Stopwatch:: class.java)
            startActivity(iB)
        }




    }
}
