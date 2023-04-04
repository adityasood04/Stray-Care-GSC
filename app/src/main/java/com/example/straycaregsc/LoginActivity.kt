package com.example.straycaregsc

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


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
        initialiseGoogleAuth()
        setListeners()
    }


//    lateinit var  signInRequest:BeginSignInRequest
//    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
//    private var showOneTapUI = true
//    private lateinit var oneTapClient: SignInClient

    private fun initialiseGoogleAuth() {

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
        if (FirebaseAuth.getInstance().currentUser != null)
        {
            finish()
            val i = Intent(this@LoginActivity,HomePageActivity::class.java)
            startActivity(i)
        }
        else{

        }
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
                finish()
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (requestCode) {
//            REQ_ONE_TAP -> {
//                try {
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = credential.googleIdToken
//                    when {
//                        idToken != null -> {
//                            processResponse(credential,idToken)
//                            Log.d("adi", "Got ID token.")
//                        }
//                        else -> {
//                            // Shouldn't happen.
//                            Log.d("adi", "No ID token!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//
//    }


//    private fun processResponse(googleCredential:SignInCredential, idToken:String){
//        if (idToken != null) {
//            // Got an ID token from Google. Use it to authenticate
//            // with Firebase.
//            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//            FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
//                .addOnCompleteListener(this,
//                    OnCompleteListener<AuthResult?> { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("adi", "signInWithCredential:success")
//                            val user: FirebaseUser =  FirebaseAuth.getInstance().currentUser!!
//                            updateUI(user)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("adi", "signInWithCredential:failure", task.exception)
//                            updateUI(null)
//                        }
//                    })
//        }
//
//
//    }
//
//    private fun updateUI(user: FirebaseUser?) {
//        if(user!=null){
//            Toast.makeText(this@LoginActivity,"Signing sucessfull",Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this@LoginActivity,HomePageActivity::class.java))
//            finish()
//        }
//        else{
//            Toast.makeText(this@LoginActivity,"Failed",Toast.LENGTH_SHORT).show()
//
//        }
//    }


}