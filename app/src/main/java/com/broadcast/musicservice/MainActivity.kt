package com.broadcast.musicservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.broadcast.musicservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this,MediaPlayerService::class.java)


        binding.btnPlayMusic.setOnClickListener {
          startForegroundService(serviceIntent)
        }
       binding.btnStopMusic.setOnClickListener {
           stopService(serviceIntent)
       }
    }
}