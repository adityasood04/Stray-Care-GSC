package com.example.straycaregsc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.straycaregsc.Models.UserModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

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
    lateinit var pbDP:ProgressBar
    lateinit var tvSetProfileImage:TextView
    lateinit var tvPersonalInfo:TextView
    lateinit var clMain:ConstraintLayout
    var userDetails = UserModel()
    lateinit var currentUser:FirebaseUser
    lateinit var  userDpPath: Uri
    lateinit var  civUserDp: CircleImageView
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

        if(userDetails.dpUrl!= null){
            showPB()
            Picasso.get().load(userDetails.dpUrl).into(civUserDp)
            hidePB()
        }
        else{
            return
        }
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
        tvSetProfileImage= findViewById(R.id.tvSetProfileImage)
        civUserDp= findViewById(R.id.civUserDp)
        pbDP= findViewById(R.id.pbDP)

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
        tvSetProfileImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


    }

    private fun uploadImage(uri:Uri,successListener: OnSuccessListener<in Uri>){
        showPB()

        val imgRef = FirebaseStorage.getInstance().reference.child("${userDetails.userMID}/${userDetails.userName}.png")
        imgRef.putFile(uri).addOnSuccessListener {
            imgRef.downloadUrl
                .addOnSuccessListener(successListener)
                .addOnFailureListener{
                    Toast.makeText(this@ProfileActivity,"Unable to upload", Toast.LENGTH_SHORT).show()
                    Log.i("adi", "error : ${it.message}")
                }
        }
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this@ProfileActivity,HomePageActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0 &&  data != null){
            userDpPath = data.data!!
            Picasso.get().load(userDpPath).into(civUserDp)
            uploadImage(userDpPath, successListener = OnSuccessListener {
                userDetails.dpUrl = it.toString()
                hidePB()
                saveUserDetails()
                Toast.makeText(this@ProfileActivity,"Updated profile image successfully.",Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun saveUserDetails() {
       FirebaseFirestore.getInstance().collection("Users")
           .document(userDetails.userMID)
           .set(userDetails)
           .addOnCompleteListener {
               if(it.isSuccessful){
                   Log.i("adi", "dp posted")
               }
               else{
                   Log.i("adi", "dp not posted")
               }
           }
    }

    private fun showPB(){
        pbDP.visibility = View.VISIBLE
    }
 private fun hidePB(){
        pbDP.visibility = View.GONE
    }


}