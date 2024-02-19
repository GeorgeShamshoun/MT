package com.example.mt.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mt.R
import com.example.mt.databinding.ActivitySecondBinding
import java.util.Locale

class SecondActivity : AppCompatActivity() , TextWatcher {
    lateinit var binding: ActivitySecondBinding
    var Tres : Int?= null
    var TtoS : TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val site = arrayOf("Com","Net","Org","gov")
        val myAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,site)
        binding.mySpinner.adapter = myAdapter
        binding.txtName2.text = "Hello  ${intent.extras?.getString("name")}"

        binding.txtWeb.addTextChangedListener (this)
        binding.txtCall.addTextChangedListener (this)
        binding.txtTalk.addTextChangedListener(this)
        binding.txtHeight.addTextChangedListener(this)
        binding.txtWeight.addTextChangedListener(this)

        TtoS = TextToSpeech(this, object :TextToSpeech.OnInitListener{
            override fun onInit(status: Int) {
                Tres = TtoS!!.setLanguage(Locale.ENGLISH) }
        })
    }

    fun onclick(view: View) {
        when (view) {
            binding.btnWeb -> {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("http://${binding.txtWeb.text}.${ binding.mySpinner.selectedItem}")
                startActivity(i) }
            binding.btnCall -> {
                val i = Intent(Intent.ACTION_DIAL)
                i.data = Uri.parse("tel:${binding.txtCall.text}")
                startActivity(i)}
            binding.btnTalk -> {
                if (Tres == TextToSpeech.LANG_NOT_SUPPORTED || Tres == TextToSpeech.LANG_MISSING_DATA) {
                    Toast.makeText(this, " Error ", Toast.LENGTH_LONG).show()
                } else {
                    TtoS!!.speak( binding.txtTalk.text, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
            binding.btnMedia ->{startActivity(Intent("ShowFileActivity").putExtra("name", binding.txtName2.text.toString()))}
            binding.btnNumbers ->{startActivity(Intent("NumbersActivity").putExtra("name", binding.txtName2.text.toString()))}
            binding.btnRoom ->{startActivity(Intent("UsersActivity").putExtra("name", binding.txtName2.text.toString()))}
            binding.btnStopWatch ->{startActivity(Intent("StopWatchActivity").putExtra("name", binding.txtName2.text.toString()))}
            binding.btnRetrofit ->{startActivity(Intent("RetrofitActivity").putExtra("name", binding.txtName2.text.toString()))}

        }
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    @SuppressLint("ResourceAsColor")
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        binding.btnWeb.isEnabled= binding.txtWeb.text.trim().isNotEmpty()
        binding.btnCall.isEnabled= binding.txtCall.text.trim().isNotEmpty()
        binding.btnTalk.isEnabled= binding.txtTalk.text.trim().isNotEmpty()

        if (binding.txtHeight.text.trim().isNotEmpty() && binding.txtWeight.text.trim().isNotEmpty()){
            binding.IBMLin1.visibility = View.VISIBLE
            binding.IBMLin2.visibility = View.VISIBLE
            binding.txtCalcBMI.setText("${((binding.txtWeight.text.toString().toFloat()/(binding.txtHeight.text.toString().toFloat()*binding.txtHeight.text.toString().toFloat()))*1000000).toInt().toFloat()/100}")
            when{
                binding.txtCalcBMI.text.toString().toFloat() < 18.50 -> {  binding.txtCalcBMIView.setText("UnderWeight")
                    binding.imageViewBMI.setImageResource(R.drawable.bmi1)}
                binding.txtCalcBMI.text.toString().toFloat() in 18.50..24.99 -> { binding.txtCalcBMIView.setText("Helthy")
                    binding.imageViewBMI.setImageResource(R.drawable.bmi2)}
                binding.txtCalcBMI.text.toString().toFloat() in 25.00..29.99 -> { binding.txtCalcBMIView.setText("OverWight")
                    binding.imageViewBMI.setImageResource(R.drawable.bmi3)}
                binding.txtCalcBMI.text.toString().toFloat() >29.99 -> { binding.txtCalcBMIView.setText("Obese")
                    binding.imageViewBMI.setImageResource(R.drawable.bmi4) }
            }
        }
        else
        { binding.IBMLin1.visibility = View.INVISIBLE
            binding.IBMLin2.visibility = View.INVISIBLE}}
    override fun afterTextChanged(s: Editable?) {}
}