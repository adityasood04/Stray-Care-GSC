package com.example.straycaregsc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.straycaregsc.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class HomePageActivity : AppCompatActivity() {
    var userDetails = UserModel()
    lateinit var uid:String
    var userDetailsDownloaded = false

    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var ivProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val user = intent.getStringExtra("user")
        uid = intent.getStringExtra("user uid").toString()
        if(user.isNullOrBlank()){
            Log.i("adi", "received object is null")
        }
        else{
            Log.i("adi", "user fetched ${userDetails.userMID} ")
            Log.i("adi", "user fetched ${userDetails.userName} ")
            Log.i("adi", "user fetched ${userDetails.passWord} ")
        }
        fetchUser()
        initialiseVariables()
        changeFragment(HomeFragment())
        setListeners()
    }

    private fun fetchUser() {
        FirebaseFirestore.getInstance().collection("Users")
            .document(uid)
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    if(it.result.exists()){
                        userDetails = it.result.toObject(UserModel::class.java)!!
                        userDetailsDownloaded = true
                    }
                }
            }

    }

    private fun setListeners() {
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> changeFragment(HomeFragment())
                R.id.vaccine -> changeFragment(VaccinationFragment())
                R.id.upload -> {
                    launchPostActivity()
                }
                R.id.adoptPet -> changeFragment(AdoptFragment())
                R.id.putForAdoption -> {
                    val i = Intent(this@HomePageActivity, PutForAdoptionActivity::class.java)
                    startActivity(i)
                }
                else -> {
                    changeFragment(HomeFragment())
                }
            }
            true
        }


        ivProfile.setOnClickListener{
            if(userDetailsDownloaded){
                val i = Intent(this@HomePageActivity,ProfileActivity::class.java)
                i.putExtra("userDetails",Gson().toJson(userDetails))
                startActivity(i)

            }
            else{
                Toast.makeText(this@HomePageActivity,"Fetching user details. Please wait and try again later",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun launchPostActivity() {
        val i = Intent(this@HomePageActivity, PostActivity::class.java)
        startActivity(i)
    }

    private fun initialiseVariables() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        ivProfile = findViewById(R.id.ivProfile)
    }
    private fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutAM,fragment)
        fragmentTransaction.commit()
    }
}