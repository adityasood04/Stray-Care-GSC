package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

class ProfileActivity : AppCompatActivity() {
    lateinit var  ivBackBtn: ImageView
    lateinit var tvUploadImg: TextView
    lateinit var tvLogOut: TextView
    lateinit var ivUploadImg:ImageView
    var userDetails = UserModel()
    lateinit var currentUser:FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
         userDetails = Gson().fromJson(intent.getStringExtra("userDetails"),UserModel::class.java)

        currentUser = FirebaseAuth.getInstance().currentUser!!
        userDetails.email = currentUser.email
        userDetails.userMID = currentUser.uid
        Log.i("adi", "current user uid = ${userDetails.userMID}")
        //Fetched user model
        //todo show details in views
        initialiseVariables()
        setListeners()



    }

    private fun setListeners() {
        tvLogOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            finish()
            startActivity(Intent(this@ProfileActivity,LoginActivity::class.java))
        }
    }

    private fun initialiseVariables() {
        tvLogOut= findViewById(R.id.tvLogOut)
    }
}