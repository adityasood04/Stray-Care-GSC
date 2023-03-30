package com.example.straycaregsc

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class OurCommunity : AppCompatActivity() {
    lateinit var ivBackBtnOC:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_our_community)
        ivBackBtnOC= findViewById(R.id.ivBackBtnOC)
        ivBackBtnOC.setOnClickListener{
            finish()
            startActivity(Intent(this@OurCommunity,HomePageActivity::class.java))

        }

    }
}