package com.example.straycaregsc

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.straycaregsc.Fragments.*
import com.example.straycaregsc.Models.UserModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class HomePageActivity : AppCompatActivity() {
    var userDetails = UserModel()
    lateinit var uid:String
    lateinit var userName:String
    lateinit var userMID:String
    lateinit var ivOurCommunity:ImageView
    var userDetailsDownloaded = false
    var isAlreadyLoggedIn = false

    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var ivProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        uid = intent.getStringExtra("user uid").toString()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            fetchUser(user.uid)
            Log.i("adi", "user uid is ${user.uid} ")
        }
        else{
            fetchUser(uid)
        }
        initialiseVariables()
        changeFragment(HomeFragment())
        setListeners()
    }

    private fun fetchUser(uid:String) {
        FirebaseFirestore.getInstance().collection("Users")
            .document(uid)
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    if(it.result.exists()){
                        userDetails = it.result.toObject(UserModel::class.java)!!
                        userName = userDetails.userName
                        userMID = userDetails.userMID
                        userDetailsDownloaded = true
                    }
                }
            }

    }

    private fun setListeners() {
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> changeFragment(HomeFragment())
                R.id.vaccine -> {
                    val gmmIntentUri = Uri.parse("geo:0,0?q=veterinary hospital")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)


                }
                R.id.upload -> {
                    finish()
                    launchPostActivity()
                }
                R.id.adoptPet -> changeFragment(AdoptFragment())
                R.id.putForAdoption -> {
                    finish()
                    if(userDetailsDownloaded){
                        val i = Intent(this@HomePageActivity, PutForAdoptionActivity::class.java)
                        i.putExtra("user",userName)
                        i.putExtra("uid",userMID)
                        startActivity(i)

                    }
                }
                else -> {
                    changeFragment(HomeFragment())
                }
            }
            true
        }


        ivProfile.setOnClickListener{
//            if(userDetailsDownloaded){
                val i = Intent(this@HomePageActivity,ProfileActivity::class.java)
                i.putExtra("userDetails",Gson().toJson(userDetails))
                startActivity(i)

//            }
//            else{
//                Toast.makeText(this@HomePageActivity,"Fetching user details. Please wait and try again later",Toast.LENGTH_SHORT).show()
//            }
        }
        ivOurCommunity.setOnClickListener {
            startActivity(Intent(this@HomePageActivity,OurCommunity::class.java))
        }
    }

    private fun launchPostActivity() {
        if(userDetailsDownloaded){
            val i = Intent(this@HomePageActivity, PostActivity::class.java)
            i.putExtra("userToPost", userDetails.userName)
            startActivity(i)

        }
    }

    private fun initialiseVariables() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        ivProfile = findViewById(R.id.ivProfile)
        ivOurCommunity = findViewById(R.id.ivOurCommunity)
    }
    private fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutAM,fragment)
        fragmentTransaction.commit()
    }
}