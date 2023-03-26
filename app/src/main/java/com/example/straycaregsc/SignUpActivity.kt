package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class SignUpActivity : AppCompatActivity() {
    lateinit var  ivBackBtn: ImageView
    lateinit var etUserNameSignUp:EditText
    lateinit var  etEmailSignUp: EditText
    lateinit var etPasswordSignUp: EditText
    lateinit var etContactNoSignUp: EditText
    lateinit var etPassWordConfirmSignUp: EditText
    lateinit var  ivSignUpGoogle: ImageView
    lateinit var tvSignUp: TextView
    lateinit var tvBackToSignIn: TextView
    lateinit var pbSignup: ProgressBar
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initialiseVariables()
        setListeners()

    }

    private fun setListeners() {
        tvSignUp.setOnClickListener {
            registerUser()
        }
        tvBackToSignIn.setOnClickListener {
            finish()
            Log.i("adi", "Starting login activity")

            val i = Intent(this@SignUpActivity,LoginActivity::class.java)
            startActivity(i)

        }

    }

    private fun registerUser() {
        showProgressBar()

        user.userName  = etUserNameSignUp.text.toString()
        user.email = etEmailSignUp.text.toString()
        user.contactNo = etContactNoSignUp.text.toString()
        user.passWord = etPasswordSignUp.text.toString()
        val confirmPassword = etPassWordConfirmSignUp.text.toString()
        if (user.email.isBlank() ||
            user.userName.isBlank() ||
            confirmPassword.isBlank() ||
            user.contactNo.isBlank() ||
            user.passWord.isBlank()||
            user.contactNo.isBlank())
        {
            hideProgressBar()
            Toast.makeText(this, "All fields are necessary", Toast.LENGTH_SHORT).show()
            return
        }
        if (user.passWord != confirmPassword) {
            hideProgressBar()
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email,user.passWord).addOnCompleteListener {
            if (it.isSuccessful) {
                user.userMID = FirebaseAuth.getInstance().uid
                Toast.makeText(this, "Successfully Signed Up successfully", Toast.LENGTH_SHORT).show()
                saveUserToFireStore()
                hideProgressBar()
                finish()
                launchNextActivity()
            } else {
                hideProgressBar()
                Log.i("adi", it.exception!!.message.toString())
                Toast.makeText(this, it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun launchNextActivity() {
        val i = Intent(this@SignUpActivity,HomePageActivity::class.java)
        val userRegistered =  Gson().toJson(user)
        i.putExtra("user",userRegistered)
        i.putExtra("from" , "signupActivity")
        startActivity(i)


    }

    private fun saveUserToFireStore() {
        user.userID = FirebaseFirestore.getInstance().collection("Users").document().id
        FirebaseFirestore.getInstance().collection("Users")
            .document(user.userMID)
            .set(user)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    hideProgressBar()
                    Toast.makeText(this@SignUpActivity,"User registered successfully",Toast.LENGTH_SHORT).show()
                }
                else{
                    hideProgressBar()
                    Toast.makeText(this@SignUpActivity,"Unable to save user Try again!!",Toast.LENGTH_SHORT).show()
                    finish()
                }
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
        etContactNoSignUp = findViewById(R.id.etContactNoSignUp)
        pbSignup = findViewById(R.id.pbSignUP)



    }
    private fun showProgressBar(){
        pbSignup.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        pbSignup.visibility = View.GONE

    }

}