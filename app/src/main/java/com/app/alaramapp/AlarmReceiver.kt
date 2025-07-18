package com.app.alaramapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.net.toUri

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val hour = intent.getIntExtra("hour", -1)
        val minute = intent.getIntExtra("minute", -1)
        val soundUriString = intent.getStringExtra("soundUri")

        val cleanUri = soundUriString?.substringBefore("?")

        val alarmUri: Uri = if (!cleanUri.isNullOrEmpty()) {
            cleanUri.toUri()
        } else {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        }

        Log.d("AlarmReceiver", "Playing alarm at $hour:$minute with URI: $alarmUri")

        try {
            val mediaPlayer = MediaPlayer().apply {
                setDataSource(context, alarmUri)
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                isLooping = true
                prepare()
                start()
            }

            // Optional auto-stop after 1 min
            Handler(Looper.getMainLooper()).postDelayed({
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }
                mediaPlayer.release()
            }, 60_000)

        } catch (e: Exception) {
            Log.e("AlarmReceiver", "Failed to play alarm sound", e)
        }
    }
}
