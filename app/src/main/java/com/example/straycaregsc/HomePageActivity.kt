package com.example.straycaregsc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.straycaregsc.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class HomePageActivity : AppCompatActivity() {
    var userDetails = UserModel()

    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var ivProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val user = intent.getStringExtra("user")
        userDetails = Gson().fromJson(user,UserModel::class.java)

        Log.i("adi", "user fetched ${userDetails.userMID} ")
        Log.i("adi", "user fetched ${userDetails.userName} ")
        Log.i("adi", "user fetched ${userDetails.passWord} ")
        initialiseVariables()
        changeFragment(HomeFragment())
        setListeners()
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
            val i = Intent(this@HomePageActivity,ProfileActivity::class.java)
            i.putExtra("userDetails",Gson().toJson(userDetails))
            startActivity(i)
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