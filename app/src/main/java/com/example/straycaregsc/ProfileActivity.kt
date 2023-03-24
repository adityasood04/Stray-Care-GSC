package com.example.straycaregsc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson

class ProfileActivity : AppCompatActivity() {
    var userDetails = UserModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
         userDetails = Gson().fromJson(intent.getStringExtra("userDetails"),UserModel::class.java)


        //Fetched user model
        //todo show details in views



    }
}