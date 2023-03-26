package com.example.straycaregsc

import android.app.Activity
import android.content.Intent
import android.media.Image
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
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class PostActivity : AppCompatActivity() {
    lateinit var  ivBackBtn:ImageView
    lateinit var tvUploadImg:TextView
    lateinit var ivUploadImg:ImageView
    lateinit var tvPostBtn:TextView
    lateinit var etCaption:EditText
    lateinit var etDescription:EditText
    lateinit var postPath:Uri
    lateinit var pbPostActivity: ProgressBar
    lateinit var  postModel:PostModel
    var  isPostImgSelected = false
    lateinit var postsMap:HashMap<String,PostModel>
    var postsArrayList = ArrayList<PostModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        initialiseVariables()
        setListeners()
    }



    //TODO Generate an user id while signing in     --- done
    // Generate post id while posting image

    private fun launchHomePageActivity() {
        val i = Intent(this@PostActivity,HomePageActivity::class.java)
        startActivity(i)
    }

    private fun savePost() {
        Log.i("adi", "save post called")
        postModel.id = FirebaseFirestore.getInstance().collection("posts").document().id
        postsArrayList.add(postModel)
        FirebaseFirestore.getInstance().collection("posts").document("global posts")
            .set(postsArrayList)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this@PostActivity,"Posted successfully",Toast.LENGTH_SHORT).show()
                    launchHomePageActivity()
                    finish()
                }
                else{
                    Log.i("adi", "${it.exception!!.message}")
                    Toast.makeText(this@PostActivity,"Error while uploading the post",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun resetPostModel() {
        postModel.id = null
        postModel.caption = null
        postModel.description = null
        postModel.imageUrl = null
    }

    private fun hideProgressBar() {
        pbPostActivity.visibility = View.GONE
    }
    private fun showProgressBar() {
        pbPostActivity.visibility = View.VISIBLE
    }

    private fun setListeners() {
        ivBackBtn.setOnClickListener{
            finish()
        }
        tvUploadImg.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


            tvPostBtn.setOnClickListener{
                postModel.caption = etCaption.text.toString()
                postModel.description = etDescription.text.toString()
                if(etCaption.text != null && etDescription.text!= null && isPostImgSelected){
                Log.i("adi", "started saving")
                showProgressBar()
                uploadImage(postPath, successListener = {
                    Log.i("adi", "post link  saved")
                    postModel.imageUrl = it.toString()
                    savePost()
                    Log.i("adi", "saved successfully")
                    hideProgressBar()
                })
            }
        }
    }

    private fun initialiseVariables() {
        ivBackBtn= findViewById(R.id.ivBackBtn)
        tvUploadImg = findViewById(R.id.tvUploadImage)
        ivUploadImg = findViewById(R.id.ivUploadImage)
        tvPostBtn = findViewById(R.id.tvSubmitPost)
        etCaption = findViewById(R.id.etCaption)
        etDescription = findViewById(R.id.etDescription)
        pbPostActivity = findViewById(R.id.pbPostActivity)
        postModel = PostModel()
    }

    private fun uploadImage(uri:Uri,successListener: OnSuccessListener<in Uri>){


        //TODO
        // Use userid to make the post url unique


        val imgRef = FirebaseStorage.getInstance().reference.child("${postModel.id}/postImage.png")
        imgRef.putFile(uri).addOnSuccessListener {
                imgRef.downloadUrl
                    .addOnSuccessListener(successListener)
                    .addOnFailureListener{
                        Toast.makeText(this@PostActivity,"Unable to upload", Toast.LENGTH_SHORT).show()
                        Log.i("adi", "error : ${it.message}")
                    }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0 &&  data != null){
            postPath = data.data!!
            Picasso.get().load(postPath).into(ivUploadImg)
            isPostImgSelected = true
        }
    }

}