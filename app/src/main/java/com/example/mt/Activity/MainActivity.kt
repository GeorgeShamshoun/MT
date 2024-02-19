package com.example.mt.Activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.mt.R
import com.example.mt.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextWatcher {

    private val ChannelID = "notificationChannel1"
    private var notificationManager : NotificationManager? = null

    private var CountAnimate = 0
    private var mediaplayer: MediaPlayer? = null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    var Tres: Int? = null
    var TtoS: TextToSpeech? = null

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(ChannelID,"M T Channel1","M T Channel1")

        binding.TxtName.addTextChangedListener(this)
        binding.TxtPhone.addTextChangedListener(this)
        binding.TxtEmail.addTextChangedListener(this)
        binding.TxtPassWord.addTextChangedListener(this)
        SetSharedPreferences()
        handler = Handler(Looper.getMainLooper())
        TtoS = TextToSpeech(this, object : TextToSpeech.OnInitListener {
            override fun onInit(status: Int) {
                Tres = TtoS!!.setLanguage(Locale.ENGLISH)
            }
        })


    }

    override fun onResume() {
        super.onResume()
        if (CountAnimate == 0) {
            CountAnimate += 1
            MediaPlayer.create(this, R.raw.sound1).start()
            Toast.makeText(this, " Multiple Tasks ", Toast.LENGTH_LONG).show()
            binding.imagelogo.visibility = View.VISIBLE
            binding.imagelogo.animate().translationY(-650f).scaleY(0.5f).scaleX(0.5f)
                .rotation(360f).duration =
                3000
            binding.imagelogo.animate().alpha(0f).duration = 4000
        }
    }

    fun onclick(view: View) {
        when (view) {
            binding.btnEnter -> {
                GetSharedPreferences()
                TtoS!!.speak("hello ${binding.TxtName.text}", TextToSpeech.QUEUE_FLUSH, null, null)
                displayNotification()
                startActivity(
                    Intent("SecondActivity").putExtra(
                        "name",
                        binding.TxtName.text.toString()
                    )
                )
            }

            binding.btnCleanup -> {
                binding.TxtName.setText(null)
                binding.TxtPhone.setText(null)
                binding.TxtEmail.setText(null)
                binding.TxtPassWord.setText(null)
            }

            binding.btnPlay -> {
                if (mediaplayer == null) {
                    mediaplayer = MediaPlayer.create(this, R.raw.sound2)
                    SeekBarFun()
                }
                mediaplayer?.start()
                binding.LayOudio.visibility = View.VISIBLE
            }

            binding.btnPause -> mediaplayer?.pause()
            binding.btnStop -> {
                mediaplayer?.stop()
                binding.tvPlaye.text = "0 sec"
                binding.tvDur.text = "${mediaplayer!!.duration / 1000} sec"
                mediaplayer?.reset()
                mediaplayer?.release()
                mediaplayer = null
                handler.removeCallbacks(runnable)
                binding.SeekBarSound.progress = 0
                binding.LayOudio.visibility = View.INVISIBLE
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        binding.btnEnter.isEnabled =
            (binding.TxtName.text.toString().trim().isNotEmpty() && binding.TxtPhone.text.toString()
                .trim()
                .isNotEmpty() && binding.TxtEmail.text.toString().trim().isNotEmpty() &&
                    binding.TxtPassWord.text.toString().trim().isNotEmpty())

        if (binding.TxtName.text.toString().trim().isEmpty() || binding.TxtPhone.text.toString()
                .trim()
                .isEmpty() || binding.TxtEmail.text.toString().trim().isEmpty() ||
            binding.TxtPassWord.text.toString().trim().isEmpty()
        ) {
            binding.txtNote.visibility = View.VISIBLE
        } else {
            binding.txtNote.visibility = View.INVISIBLE
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
    fun GetSharedPreferences() {
        val Ed = getSharedPreferences("LogInFile", Context.MODE_PRIVATE).edit()
        Ed.putString("Key_Name", binding.TxtName.text.toString())
        Ed.putString("Key_Phone", binding.TxtPhone.text.toString())
        Ed.putString("Key_Email", binding.TxtEmail.text.toString())
        Ed.putString("Key_Password", binding.TxtPassWord.text.toString())
        Ed.apply()
    }

    fun SetSharedPreferences() {
        binding.TxtName.setText(
            getSharedPreferences(
                "LogInFile",
                Context.MODE_PRIVATE
            ).getString("Key_Name", "")
        )
        binding.TxtPhone.setText(
            getSharedPreferences(
                "LogInFile",
                Context.MODE_PRIVATE
            ).getString("Key_Phone", "")
        )
        binding.TxtEmail.setText(
            getSharedPreferences(
                "LogInFile",
                Context.MODE_PRIVATE
            ).getString("Key_Email", "")
        )
        binding.TxtPassWord.setText(
            getSharedPreferences(
                "LogInFile",
                Context.MODE_PRIVATE
            ).getString("Key_Password", "")
        )
    }

    fun SeekBarFun() {
        binding.SeekBarSound.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaplayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.SeekBarSound.max = mediaplayer!!.duration
        runnable = Runnable {
            binding.SeekBarSound.progress = mediaplayer!!.currentPosition

            binding.tvPlaye.text = "${mediaplayer!!.currentPosition / 1000} sec"
            binding.tvDur.text =
                "${mediaplayer!!.duration / 1000 - mediaplayer!!.currentPosition / 1000} sec"
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        CountAnimate += 1
        outState.putInt("Key1", CountAnimate)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        CountAnimate = savedInstanceState.getInt("Key1")
    }

    private fun displayNotification(){
        val notificationId = 1
        val notification = NotificationCompat.Builder(this@MainActivity,ChannelID)
            .setContentTitle("M T")
            .setContentText("Welcome Multiple Tasks ")
            .setSmallIcon(R.drawable.icon)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager?.notify(notificationId,notification)
    }
    private fun createNotificationChannel(id:String,name:String,channelDeprecation: String){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel (id,name,importance).apply {
            description = channelDeprecation
        }
        notificationManager?.createNotificationChannel(channel)
    }
    }
}