package com.example.mt.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.mt.databinding.ActivityShowFileBinding


class ShowFileActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtName2.text = "${intent.extras?.getString("name")}"
    }

    @SuppressLint("IntentReset")
    fun onclick(view: View) {
        when (view) {
            binding.btnChooseImage -> {
                val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(i, 100)
            }

            binding.btnChooseVideo -> {
                val i = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(i, 200)
            }

            binding.btnChooseAny -> {
                val i = Intent(Intent.ACTION_VIEW, MediaStore.Downloads.INTERNAL_CONTENT_URI)
                i.type = "*/*"
                this.startActivity(i)
            }

            binding.btnCam -> {
                var iv = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                startActivityForResult(iv, 300)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val uri = data.data

            binding.ImageView.setImageURI(uri)
            binding.ImageView.visibility = View.VISIBLE
            binding.VideoView.visibility = View.GONE
        } else if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            binding.VideoView!!.setMediaController(MediaController(this))
            binding.VideoView.setVideoURI(uri)
            binding.VideoView.start()
            binding.VideoView.visibility = View.VISIBLE
            binding.ImageView.visibility = View.GONE

        } else if (requestCode == 300 && resultCode == Activity.RESULT_OK && data != null) {

            binding.VideoView!!.setMediaController(android.widget.MediaController(this))
            binding.VideoView.start()
            binding.VideoView.setVideoURI(data.data)
            binding.VideoView.visibility = View.VISIBLE
            binding.ImageView.visibility = View.GONE
        }
    }

}