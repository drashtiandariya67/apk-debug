package com.example.callrecorder

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaRecorder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.io.File

class RecorderService : Service() {
    private var recorder: MediaRecorder? = null
    private var filePath: String? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val number = intent?.getStringExtra("number") ?: "unknown"
        filePath = "${filesDir.absolutePath}/${System.currentTimeMillis()}_$number.mp3"

        recorder = MediaRecorder().apply {
            try {
                if (DeviceUtils.hasNativeCallRecording()) {
                    setAudioSource(MediaRecorder.AudioSource.VOICE_CALL)
                } else {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    forceSpeakerphone(this@RecorderService, true)
                }
            } catch (e: Exception) {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                forceSpeakerphone(this@RecorderService, true)
            }

            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(filePath)
            prepare()
            start()
        }

        val notification = NotificationCompat.Builder(this, "recorder_channel")
            .setContentTitle("Recording Call")
            .setContentText(number)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        filePath?.let { DriveUploader.upload(applicationContext, it) }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun forceSpeakerphone(context: Context, enable: Boolean) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.mode = AudioManager.MODE_IN_CALL
        audioManager.isSpeakerphoneOn = enable
    }
}
