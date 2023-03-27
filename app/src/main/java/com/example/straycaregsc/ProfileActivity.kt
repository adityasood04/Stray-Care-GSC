package com.example.straycaregsc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson

class ProfileActivity : AppCompatActivity() {
    lateinit var  ivBackBtn: ImageView
    lateinit var tvUploadImg: TextView
    lateinit var ivUploadImg:ImageView
    var userDetails = UserModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
         userDetails = Gson().fromJson(intent.getStringExtra("userDetails"),UserModel::class.java)


        //Fetched user model
        //todo show details in views



    }
}