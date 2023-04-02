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
import com.example.straycaregsc.Models.GlobalPostsModel
import com.example.straycaregsc.Models.PostModel
import com.example.straycaregsc.Models.UserModel
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
    private lateinit var etDescription:EditText
    lateinit var postPath:Uri
    private lateinit var pbPostActivity: ProgressBar
    lateinit var uid: String
    private var  postModel = PostModel()
    var  user = UserModel()
    lateinit var  globalPostsModel: GlobalPostsModel

    var  isPostImgSelected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        initialiseVariables()
        if(intent.getStringExtra("uid")!=null){
            uid = intent.getStringExtra("uid").toString()
            fetchUser(uid)
        }
        fetchPreviousPosts()
        setListeners()
    }

    private fun fetchUser(uid: String) {
        FirebaseFirestore.getInstance().collection("Users")
            .document(uid)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    if(it.result.exists()){
                        user = it.result.toObject(UserModel::class.java)!!
                        if(user.dpUrl!=null){
                            postModel.userDp = user.dpUrl
                        }
                        else{
                            return@addOnCompleteListener
                        }
                    }
                    else{
                        Log.i("adi", "fetchUser in post activity: result null ")
                    }
                }
            }
    }


    private fun launchHomePageActivity() {
        val i = Intent(this@PostActivity,HomePageActivity::class.java)
        startActivity(i)
    }

    private fun fetchPreviousPosts(){
        FirebaseFirestore.getInstance().collection("posts")
            .document("global posts")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    if(it.result.exists()){
                        globalPostsModel = it.result.toObject(GlobalPostsModel::class.java)!!
                    }
                    else{
                        return@addOnCompleteListener
                    }
                }
                else{
                    Log.i("adi", "fetchPreviousPosts: error encountered ")
                }
            }
    }


    private fun savePost() {
        Log.i("adi", "save post called")
        postModel.id = FirebaseFirestore.getInstance().collection("posts").document().id

        globalPostsModel.postsArray.add(postModel)
        FirebaseFirestore.getInstance().collection("posts").document("global posts")
            .set(globalPostsModel)
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
            onBackPressed()
        }
        tvUploadImg.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


            tvPostBtn.setOnClickListener{
                postModel.caption = etCaption.text.toString()
                postModel.description = etDescription.text.toString()
                postModel.user = intent.getStringExtra("userToPost")!!
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
        globalPostsModel = GlobalPostsModel()
    }

    private fun uploadImage(uri:Uri,successListener: OnSuccessListener<in Uri>){


        //TODO
        // Use userid to make the post url unique


        val imgRef = FirebaseStorage.getInstance().reference.child("${postModel.user}/${postModel.caption}/postImage.png")
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

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this@PostActivity,HomePageActivity::class.java))
    }

}