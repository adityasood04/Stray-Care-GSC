package com.example.straycaregsc

import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class PostActivity : AppCompatActivity() {
    lateinit var  ivBackBtn:ImageView
    lateinit var tvUploadImg:TextView
    lateinit var ivUploadImg:ImageView
    val REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        initialiseVariables()
        setListeners()
    }

    private fun setListeners() {
        ivBackBtn.setOnClickListener{
            finish()
        }
        tvUploadImg.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    private fun initialiseVariables() {
        ivBackBtn= findViewById(R.id.ivBackBtn)
        tvUploadImg = findViewById(R.id.tvUploadImage)
        ivUploadImg = findViewById(R.id.ivUploadImage)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0){
            ivUploadImg.setImageURI(data?.data) // handle chosen image
        }
    }

}