package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.straycaregsc.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class HomePageActivity : AppCompatActivity() {
    var userDetails = UserModel()
    lateinit var uid:String
    lateinit var user:String
    var userDetailsDownloaded = false

    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var ivProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        uid = intent.getStringExtra("user uid").toString()


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
                        user = userDetails.userName
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
        if(userDetailsDownloaded){
            val i = Intent(this@HomePageActivity, PostActivity::class.java)
            i.putExtra("userToPost", userDetails.userName)
            startActivity(i)

        }
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