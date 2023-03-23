package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
    lateinit var  ivBackBtn: ImageView
    lateinit var etUserNameSignUp:EditText
    lateinit var  etEmailSignUp: EditText
    lateinit var etPasswordSignUp: EditText
    lateinit var etPassWordConfirmSignUp: EditText
    lateinit var  ivSignUpGoogle: ImageView
    lateinit var tvSignUp: TextView
    lateinit var tvBackToSignIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initialiseVariables()
        setListeners()
    }

    private fun setListeners() {
        tvSignUp.setOnClickListener {
            val i = Intent(this@SignUpActivity,HomePageActivity::class.java)
            startActivity(i)
        }
        tvBackToSignIn.setOnClickListener {
            val i = Intent(this@SignUpActivity,LoginActivity::class.java)
            startActivity(i)
        }

    }

    private fun initialiseVariables() {
        ivBackBtn = findViewById(R.id.ivBackBtn)
        etUserNameSignUp = findViewById(R.id.etUserNameSignUp)
        etEmailSignUp = findViewById(R.id.etEmailSignUp)
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp)
        etPassWordConfirmSignUp = findViewById(R.id.etPassWordConfirmSignUp)
        ivSignUpGoogle = findViewById(R.id.ivSignUpGoogle)
        tvSignUp = findViewById(R.id.tvSignUp)
        tvBackToSignIn = findViewById(R.id.tvBackToSignIn)



    }


}