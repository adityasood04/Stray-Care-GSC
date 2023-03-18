package com.example.straycaregsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.app.straycare.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePageActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var ivProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        initialiseVariables()
        changeFragment(HomeFragment())
        setListeners()
    }
    private fun setListeners() {
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> changeFragment(HomeFragment())
                R.id.vaccine -> changeFragment(VaccinationFragment())
                R.id.upload -> changeFragment(UploadFragment())
                R.id.adoptPet -> changeFragment(AdoptFragment())
                R.id.putForAdoption -> changeFragment(PutAdoptionFragment())
                else -> {
                    changeFragment(HomeFragment())
                }

            }
            true
        }

        ivProfile.setOnClickListener{
            val i = Intent(this@HomePageActivity, ProfileActivity::class.java)
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