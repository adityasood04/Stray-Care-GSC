package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.straycaregsc.Models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

class ProfileActivity : AppCompatActivity() {
    lateinit var  ivBackBtn: ImageView
    lateinit var tvUploadImg: TextView
    lateinit var tvLogOut: TextView
    lateinit var ivUploadImg:ImageView
    lateinit var ivBackFromInfo:ImageView
    lateinit var ivBackBtnPA:ImageView
    lateinit var llInfo:LinearLayout
    lateinit var tvUserName:TextView
    lateinit var tvEmail:TextView
    lateinit var tvContact:TextView
    lateinit var tvPersonalInfo:TextView
    lateinit var clMain:ConstraintLayout
    var userDetails = UserModel()
    lateinit var currentUser:FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initialiseVariables()
        userDetails = Gson().fromJson(intent.getStringExtra("userDetails"),
            UserModel::class.java)

        currentUser = FirebaseAuth.getInstance().currentUser!!
        userDetails.email = currentUser.email
        userDetails.userMID = currentUser.uid
        tvEmail.text = userDetails.email
        tvContact.text = userDetails.contactNo
        tvUserName.text = userDetails.userName
        Log.i("adi", "current user uid = ${userDetails.userMID}")
        //Fetched user model
        //todo show details in views
        setListeners()
    }



    private fun initialiseVariables() {
        tvLogOut= findViewById(R.id.tvLogOut)
        llInfo= findViewById(R.id.llInfo)
        clMain= findViewById(R.id.clMain)
        tvUserName= findViewById(R.id.tvUserNamePA)
        tvEmail= findViewById(R.id.tvEmailPA)
        tvContact= findViewById(R.id.tvContactPA)
        clMain= findViewById(R.id.clMain)
        tvPersonalInfo= findViewById(R.id.tvPersonalInfo)
        ivBackFromInfo= findViewById(R.id.ivBackFromInfo)
        tvEmail= findViewById(R.id.tvEmailPA)
        ivBackBtnPA= findViewById(R.id.ivBackBtnPA)

    }

    private fun setListeners() {
        tvLogOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            finish()
            startActivity(Intent(this@ProfileActivity,LoginActivity::class.java))
        }
        tvPersonalInfo.setOnClickListener{
            clMain.visibility = View.GONE
            llInfo.visibility = View.VISIBLE
        }
        ivBackFromInfo.setOnClickListener {
            clMain.visibility = View.VISIBLE
            llInfo.visibility = View.GONE
        }
        ivBackBtnPA.setOnClickListener{
            onBackPressed()
        }


    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this@ProfileActivity,HomePageActivity::class.java))
    }


}