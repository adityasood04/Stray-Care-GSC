package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    lateinit var tvSignUp:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialiseVariables()
        setListeners()
    }

    private fun setListeners() {
        tvSignUp.setOnClickListener{
            val i = Intent(this@LoginActivity,HomePageActivity::class.java)
            startActivity(i)
        }
    }

    private fun initialiseVariables() {
        tvSignUp= findViewById(R.id.tvSignUp)
    }
}