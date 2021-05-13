package com.broadcast.musicservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BroadCastMusic:BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        val music = MediaPlayerService().getMusic()


        Log.d("TAG", "calling")
        GlobalScope.launch(Dispatchers.Default) {

            when (intent?.action) {
                BROADCAST_ACTION_PAUSE -> if (music.isPlaying){
                    music.apply {
                        Log.d("TAG", "Pause")
                        pause()
                    }
                }
                BROADCAST_ACTION_PLAY ->  music.apply {
                    Log.d("TAG", "Start")

                    start()


                }
                BROADCAST_ACTION_STOP -> music.apply {
                    Log.d("TAG", "Stop")
                    stop()
                    prepare()

                }
            }
        }

    }
}