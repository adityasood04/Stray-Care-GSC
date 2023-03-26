package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var tvSignIn:TextView
    lateinit var tvSignUp:TextView
    lateinit var etEmail:EditText
    lateinit var etPassword:EditText
    lateinit var pbLoginActivity:ProgressBar
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
        showProgressBar()
        val email = etEmail.text.toString()
        val pass = etPassword.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                val uid = it.result.user!!.uid
                Log.i("adi", "loginUser: $uid ")
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val i = Intent(this@LoginActivity,HomePageActivity::class.java)
                i.putExtra("user uid",uid)
                startActivity(i)
                hideProgressBar()

            } else{
                hideProgressBar()
                Toast.makeText(this, "Wrong email or password ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initialiseVariables() {
        tvSignIn= findViewById(R.id.tvSignIn)
        tvSignUp= findViewById(R.id.tvSignUpLoginActivity)
        etEmail= findViewById(R.id.etEmailLogin)
        etPassword= findViewById(R.id.etPasswordLogin)
        pbLoginActivity= findViewById(R.id.pbLoginActivity)
    }

    private fun hideProgressBar() {
        pbLoginActivity.visibility = View.GONE
    }
    private fun showProgressBar() {
        pbLoginActivity.visibility = View.VISIBLE
    }

}