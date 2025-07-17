package com.app.alaramapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val hour = intent.getIntExtra("hour", -1)
        val minute = intent.getIntExtra("minute", -1)
        val alarmId = intent.getIntExtra("alarmId", -1)

        // Trigger your notification or UI update
        //Toast.makeText(context, "Alarm Triggered: $hour:$minute", Toast.LENGTH_LONG).show()
    }
}
