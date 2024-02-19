package com.example.mt.Activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mt.StopWatchService
import com.example.mt.databinding.ActivityStopWatchBinding
import kotlin.math.roundToInt

class StopWatchActivity : AppCompatActivity() {
    lateinit var binding: ActivityStopWatchBinding

    private var isStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startOrStop()
        }
        binding.btnReset.setOnClickListener {
            reset()
        }
        serviceIntent = Intent(applicationContext, StopWatchService::class.java)
        registerReceiver(updateTime, IntentFilter(StopWatchService.UPDATED_TIME))
    }

    private fun startOrStop() {
        if (isStarted) {
            stop()
        } else {
            start()
        }
    }

    private fun start() {
        serviceIntent.putExtra(StopWatchService.CURRENT_TIME, time)
        startService(serviceIntent)
        binding.btnStart.text = "Stop"
        isStarted = true
    }

    private fun stop() {
        stopService(serviceIntent)
        binding.btnStart.text = "Start"
        isStarted = false
    }

    private fun reset() {
        stop()
        time = 0.0
        binding.tvTimer.text = getFormattedTime(time)
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(StopWatchService.CURRENT_TIME, 0.0)
            binding.tvTimer.text = getFormattedTime(time)
        }
    }

    private fun getFormattedTime(time: Double): String {
        val tinmeInt = time.roundToInt()
        val hours = tinmeInt % 86400 / 3600
        val miuntes = tinmeInt % 86400 % 3600 / 60
        val seconds = tinmeInt % 86400 % 3600 % 60
        return String.format("%02d:%02d:%02d", hours, miuntes, seconds)
    }
}