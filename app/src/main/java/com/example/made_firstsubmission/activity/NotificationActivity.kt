package com.example.made_firstsubmission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.made_firstsubmission.R
import com.example.made_firstsubmission.alarm.AlarmReceiver
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val alarm = AlarmReceiver()
        switchMissNotification.setOnCheckedChangeListener { x, b ->
            if(b){
                alarm.setRepeatingAlarm(this)
            }
            else{
                alarm.cancelAlarm(this,"type")
            }
        }

        switchNewNotification.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                alarm.setRepeatingAlarmNewMovie(this)
            }
            else{
                alarm.cancelAlarm(this,"release")
            }
        }
    }
}
