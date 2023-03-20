package com.example.straycaregsc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class PutForAdoptionActivity : AppCompatActivity() {
    private lateinit var backBtn:ImageView
    private lateinit var tvUploadPetImage:TextView
    private lateinit var ivPetImage:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_put_for_adoption)
        initialiseVariables()
        setListeners()
    }

    private fun setListeners() {
        backBtn.setOnClickListener{
            finish()
        }
        tvUploadPetImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        ivPetImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }
    }

    private fun initialiseVariables() {
        backBtn = findViewById(R.id.ivBackBtnPFA)
        tvUploadPetImage = findViewById(R.id.tvUploadPetImage)
        ivPetImage = findViewById(R.id.ivPetImage)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            ivPetImage.setImageURI(data?.data)
        }
    }
}