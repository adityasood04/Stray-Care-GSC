package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var tvSignIn:TextView
    lateinit var tvSignUp:TextView
    lateinit var etEmail:EditText
    lateinit var etPassword:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialiseVariables()
        setListeners()
    }

    private fun setListeners() {
        tvSignIn.setOnClickListener{
            loginUser()

        }
        tvSignUp.setOnClickListener(View.OnClickListener {
            finish()
            val i = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(i)
        })
    }

    private fun loginUser() {
        val email = etEmail.text.toString()
        val pass = etPassword.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val i = Intent(this@LoginActivity,HomePageActivity::class.java)
                startActivity(i)

            } else{
                Toast.makeText(this, "Wrong email or password ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initialiseVariables() {
        tvSignIn= findViewById(R.id.tvSignIn)
        tvSignUp= findViewById(R.id.tvSignUpLoginActivity)
        etEmail= findViewById(R.id.etEmailLogin)
        etPassword= findViewById(R.id.etPasswordLogin)
    }

}