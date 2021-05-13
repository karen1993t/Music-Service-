package com.broadcast.musicservice

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val  BROADCAST_ACTION_PAUSE = "Pausing";
const val  BROADCAST_ACTION_PLAY = "Play";
const val  BROADCAST_ACTION_STOP = "Stop";
const val CHANNEL_Id = "music_notification"

lateinit var  mediaPlayer:MediaPlayer
class MediaPlayerService:Service() {



    override fun onBind(p0: Intent?): IBinder? {
       return  null
    }



    override fun onCreate() {
        super.onCreate()

         mediaPlayer = MediaPlayer.create(applicationContext,R.raw.arkadi_dum)

         mediaPlayer?.isLooping = false


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        GlobalScope.launch (Dispatchers.Default) {
            mediaPlayer?.start()

           createNotificationChannel()
        }
        var max = mediaPlayer.duration
        val notificationIntentPause = Intent(this,BroadCastMusic::class.java)
        notificationIntentPause.action = BROADCAST_ACTION_PAUSE
        val notificationIntentPlay = Intent(this,BroadCastMusic::class.java)
        notificationIntentPlay.action = BROADCAST_ACTION_PLAY
        val notificationIntentStop = Intent(this,BroadCastMusic::class.java)
        notificationIntentStop.action = BROADCAST_ACTION_STOP
        val intentActivityShow = Intent(this,MainActivity::class.java)

        val pendingIntentPause = PendingIntent.getBroadcast(this,0
        ,notificationIntentPause,0)
        val pendingIntentPlay = PendingIntent.getBroadcast(this,0,
                notificationIntentPlay,0)
        val pendingIntentStop = PendingIntent.getBroadcast(this,0,
                notificationIntentStop,0)

        val pendingIntentActivity = PendingIntent.getActivity(this,1,
        intentActivityShow,0)

        val notification = NotificationCompat.Builder(this, CHANNEL_Id)
            .setContentTitle("Arkadi Dumikyan")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_baseline_play_arrow_24)

            .addAction(R.drawable.ic_baseline_pause_circle_outline_24,
            "Pause", pendingIntentPause)
                .addAction(R.drawable.ic_baseline_play_arrow_24,
                        "Play", pendingIntentPlay)
                .addAction(R.drawable.ic_baseline_stop_circle_24,
                "Stop",pendingIntentStop)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1)
                        .setShowActionsInCompactView(0)
                        .setShowActionsInCompactView(2))
                        .setAutoCancel(true)
                .setProgress(max,0,true)
                .setContentIntent(pendingIntentActivity)
                .build()
    startForeground(1,notification)

       return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }


    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_Id,"Service Channel"
            ,NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }
    fun  getMusic():MediaPlayer{
    if (mediaPlayer != null){
        return mediaPlayer
    }
        return  MediaPlayer.create(applicationContext,R.raw.arkadi_dum)
    }
}