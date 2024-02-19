package com.example.mt.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mt.NumberAdapter
import com.example.mt.NumberC
import com.example.mt.R
import com.example.mt.databinding.ActivityNumbersBinding
import java.util.Locale

class NumbersActivity : AppCompatActivity() {
    lateinit var binding: ActivityNumbersBinding

    var Tres: Int? = null
    var TtoS: TextToSpeech? = null
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNumbersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtName2.text = "${intent.extras?.getString("name")}"
        handler = Handler(Looper.getMainLooper())
        TtoS = TextToSpeech(this, object : TextToSpeech.OnInitListener {
            override fun onInit(status: Int) {
                Tres = TtoS!!.setLanguage(Locale.ENGLISH)
            }
        })
        val numberslist = ArrayList<NumberC>()
        numberslist.add(NumberC("One", "one _ O N E _ 1 1 1 ", R.drawable.one))
        numberslist.add(NumberC("Two", "two _ T W O _ 2 2 2 ", R.drawable.two))
        numberslist.add(NumberC("Three", "three _ T H R E E _ 3 3 3", R.drawable.three))
        numberslist.add(NumberC("Four", "four _ F O U R _ 4 4 4 ", R.drawable.four))
        numberslist.add(NumberC("Five", "five _ F I V E _ 5 5 5", R.drawable.five))
        numberslist.add(NumberC("Six", "six _ S I X  _ 6 6 6", R.drawable.six))
        numberslist.add(NumberC("Seven", "seven _ S E V E N _ 7 7 7", R.drawable.seven))
        numberslist.add(NumberC("Eight", "eight _ E I G H T _ 8 8 8", R.drawable.eight))
        numberslist.add(NumberC("Nine", "nine _ N I N E _ 9 9 9 ", R.drawable.nine))
        numberslist.add(NumberC("Ten", "ten _ T E N _ 10 10 10 ", R.drawable.ten))

        val myAdapter = NumberAdapter(this, numberslist)
        binding.myListView.adapter = myAdapter
        binding.myListView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    TtoS!!.speak("1   o.n.e", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "One 1 ", Toast.LENGTH_LONG).show()
                }

                1 -> {
                    TtoS!!.speak("2   t.w.o", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "two 2 ", Toast.LENGTH_LONG).show()
                }

                2 -> {
                    TtoS!!.speak("3   t.h.r.e.e", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "three 3 ", Toast.LENGTH_LONG).show()
                }

                3 -> {
                    TtoS!!.speak("4   f.o.u.r", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "four 4 ", Toast.LENGTH_LONG).show()
                }

                4 -> {
                    TtoS!!.speak("5   F.i.v.e", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "Five 5 ", Toast.LENGTH_LONG).show()
                }

                5 -> {
                    TtoS!!.speak("6   S.i.x", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "Six 6 ", Toast.LENGTH_LONG).show()
                }

                6 -> {
                    TtoS!!.speak("7   S.e.v.e.n", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "Seven 7 ", Toast.LENGTH_LONG).show()
                }

                7 -> {
                    TtoS!!.speak("8   E.i.g.h.t", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "Eight 8 ", Toast.LENGTH_LONG).show()
                }

                8 -> {
                    TtoS!!.speak("9   N.i.n.e", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "Nine 9 ", Toast.LENGTH_LONG).show()
                }

                9 -> {
                    TtoS!!.speak("10   T.e.n", TextToSpeech.QUEUE_FLUSH, null, null)
                    Toast.makeText(this, "Ten 10 ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}