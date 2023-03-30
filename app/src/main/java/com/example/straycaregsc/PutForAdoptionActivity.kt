package com.example.straycaregsc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlin.system.measureTimeMillis

class PutForAdoptionActivity : AppCompatActivity() {
    private lateinit var backBtn:ImageView
    private lateinit var tvUploadPetImage:TextView
    private lateinit var ivPetImage:ImageView
    lateinit var etSpecialConsiderations:EditText
    lateinit var etCaptionOfPet:EditText
    lateinit var etBreedOfPet:EditText
    lateinit var etLocation:EditText
    lateinit var etDetailsOfPet:EditText
    lateinit var tvSubmitPostOfAdoption:TextView
    lateinit var pbPFAA:ProgressBar
    var postsFetched = false
    private lateinit var userName:String
    lateinit var adoptPostUrl:Uri
    var isImageSelected = false


    lateinit var adoptPostsModel: AdoptPostsModel
    lateinit var  adoptArrayModel: AdoptArrayModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_put_for_adoption)
        initialiseVariables()
       userName = intent.getStringExtra("user")!!
        adoptPostsModel.userName = userName
        fetchPreviousPosts()
        setListeners()
    }

    private fun fetchPreviousPosts() {
        FirebaseFirestore.getInstance().collection("adopt posts")
            .document("all posts")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    postsFetched = true
                    if(it.result.exists()){
                        val result = it.result.toObject(AdoptArrayModel::class.java)
                        adoptArrayModel.adoptPostsArray = result!!.adoptPostsArray
                    }
                    else{
                        Log.i("adi", "fetchPreviousPosts: no data found ")
                    }
                }
            }
    }

    private fun initialiseVariables() {
        backBtn = findViewById(R.id.ivBackBtnPFA)
        tvUploadPetImage = findViewById(R.id.tvUploadPetImage)
        ivPetImage = findViewById(R.id.ivPetImage)
        etSpecialConsiderations = findViewById(R.id.etSpecialConsiderations)
        etCaptionOfPet= findViewById(R.id.etCaptionOfPet)
        etBreedOfPet= findViewById(R.id.etBreedOfPet)
        etLocation= findViewById(R.id.etLocation)
        etDetailsOfPet= findViewById(R.id.etDetailsOfPet)
        tvSubmitPostOfAdoption= findViewById(R.id.tvSubmitPostOfAdoption)
        pbPFAA= findViewById(R.id.pbPFAA)
        adoptPostsModel = AdoptPostsModel()
        adoptArrayModel = AdoptArrayModel()



    }

    private fun saveAdoptPetPost(){
        if(postsFetched){
            if(etCaptionOfPet.text.isNullOrBlank()||
                etLocation.text.isNullOrBlank()||
                etBreedOfPet.text.isNullOrBlank()||
                etDetailsOfPet.text.isNullOrBlank()||
                etLocation.text.isNullOrBlank()){
                Toast.makeText(this@PutForAdoptionActivity, "Fill out all the details.",Toast.LENGTH_SHORT).show()
            }
            else{
                showProgressbar()
                adoptPostsModel.caption = etCaptionOfPet.text.toString()
                adoptPostsModel.breed = etBreedOfPet.text.toString()
                adoptPostsModel.considerations = etSpecialConsiderations.text.toString()
                adoptPostsModel.details = etDetailsOfPet.text.toString()
                adoptPostsModel.location = etLocation.text.toString()
                uploadImage(adoptPostUrl, successListener = {
                    Log.i("adi", "${adoptPostUrl}")
                    adoptPostsModel.imageUrl = it.toString()
                    adoptArrayModel.adoptPostsArray.add(adoptPostsModel)
                    FirebaseFirestore.getInstance().collection("adopt posts")
                        .document("all posts")
                        .set(adoptArrayModel)
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                hideProgressBar()
                                Toast.makeText(this@PutForAdoptionActivity,"Posted successfully",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@PutForAdoptionActivity,HomePageActivity::class.java))
                            }
                            else{
                                hideProgressBar()
                                Toast.makeText(this@PutForAdoptionActivity,"Post unsuccessful",Toast.LENGTH_SHORT).show()

                            }
                        }
                })

            }
        }
        else{
            Toast.makeText(this@PutForAdoptionActivity,"Saving post taking unusual time. Try again..",Toast.LENGTH_SHORT).show()
        }

    }

    private fun setListeners() {
        backBtn.setOnClickListener{
            onBackPressed()
        }
        tvUploadPetImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        ivPetImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }
        tvSubmitPostOfAdoption.setOnClickListener(View.OnClickListener {
            saveAdoptPetPost()

        })
    }
    private fun uploadImage(uri: Uri, successListener: OnSuccessListener<in Uri>){

        val imgRef = FirebaseStorage.getInstance().reference.child("${userName}/${System.currentTimeMillis()}/adoptPostImage.png")
        imgRef.putFile(uri).addOnSuccessListener {
            imgRef.downloadUrl
                .addOnSuccessListener(successListener)
                .addOnFailureListener{
                    Toast.makeText(this@PutForAdoptionActivity,"Unable to upload", Toast.LENGTH_SHORT).show()
                    Log.i("adi", "error : ${it.message}")
                }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1&&  data != null){
                adoptPostUrl = data.data!!
               Picasso.get()
                   .load(adoptPostUrl)
                   .into(ivPetImage)
               isImageSelected = true

        }
    }
    private fun showProgressbar(){
        pbPFAA.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        pbPFAA.visibility = View.GONE
    }
    override fun onBackPressed() {
        finish()
        startActivity(Intent(this@PutForAdoptionActivity,HomePageActivity::class.java))
    }
}