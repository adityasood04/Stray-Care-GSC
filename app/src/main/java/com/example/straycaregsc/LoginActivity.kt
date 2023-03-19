package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    lateinit var btnTemp:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialiseVariables()
        setListeners()
    }

    private fun setListeners() {
        btnTemp.setOnClickListener{
            val i = Intent(this@LoginActivity,HomePageActivity::class.java)
            startActivity(i)
        }
    }

    private fun initialiseVariables() {
        btnTemp= findViewById(R.id.btnTemp)
    }
}