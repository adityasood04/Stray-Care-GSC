package com.example.straycaregsc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson

class ProfileActivity : AppCompatActivity() {
    var userDetails = UserModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
         userDetails = Gson().fromJson(intent.getStringExtra("userDetails"),UserModel::class.java)
        Log.i("adi", "userdetails : ${userDetails.userName} ")
        Log.i("adi", "userdetails : ${userDetails.userID} ")
        Log.i("adi", "userdetails : ${userDetails.passWord} ")
        Log.i("adi", "userdetails : ${userDetails.contactNo} ")
        Log.i("adi", "userdetails : ${userDetails.email} ")



    }
}