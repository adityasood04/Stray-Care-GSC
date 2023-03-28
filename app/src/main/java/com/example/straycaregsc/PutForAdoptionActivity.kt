package com.example.straycaregsc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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
    var postsFetched = false

    lateinit var adoptPostsModel: AdoptPostsModel
    lateinit var  adoptArrayModel: AdoptArrayModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_put_for_adoption)
        initialiseVariables()
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
        adoptPostsModel = AdoptPostsModel()
        adoptArrayModel = AdoptArrayModel()



    }

    private fun saveAdoptPetPost(){
        if(postsFetched){
            if(etCaptionOfPet.text.isNullOrBlank()||
                etLocation.text.isNullOrBlank()||
                etBreedOfPet.text.isNullOrBlank()||
                etDetailsOfPet.text.isNullOrBlank()||
                etLocation.text.isNullOrBlank() ){
                Toast.makeText(this@PutForAdoptionActivity, "Fill out all the details.",Toast.LENGTH_SHORT).show()
            }
            else{
                adoptPostsModel.caption = etCaptionOfPet.text.toString()
                adoptPostsModel.breed = etBreedOfPet.text.toString()
                adoptPostsModel.considerations = etSpecialConsiderations.text.toString()
                adoptPostsModel.details = etDetailsOfPet.text.toString()
                adoptPostsModel.location = etLocation.text.toString()
                adoptArrayModel.adoptPostsArray.add(adoptPostsModel)
                FirebaseFirestore.getInstance().collection("adopt posts")
                    .document("all posts")
                    .set(adoptArrayModel)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this@PutForAdoptionActivity,"Posted successfully",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@PutForAdoptionActivity,"Posted successfully",Toast.LENGTH_SHORT).show()

                        }
                    }
            }
        }
        else{
            Toast.makeText(this@PutForAdoptionActivity,"Saving post taking unusual time. Try again..",Toast.LENGTH_SHORT).show()
        }

    }

    private fun setListeners() {
        backBtn.setOnClickListener{
            finish()
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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            ivPetImage.setImageURI(data?.data)
        }
    }
}