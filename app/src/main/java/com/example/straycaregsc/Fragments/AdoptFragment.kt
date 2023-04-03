package com.example.straycaregsc.Fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Adapters.AdoptPetAdapter
import com.example.straycaregsc.Models.AdoptArrayModel
import com.example.straycaregsc.Models.AdoptPostsModel
import com.example.straycaregsc.Models.UserModel
import com.example.straycaregsc.R
import com.google.firebase.firestore.FirebaseFirestore

class AdoptFragment : Fragment() {
    lateinit var adoptArrayModel: AdoptArrayModel
    lateinit var rcvAdoptPet: RecyclerView
    lateinit var llContactUser: LinearLayout
    lateinit var llDetails: LinearLayout
    lateinit var tvOwnerMobile: TextView
    lateinit var tvOwnerName: TextView
    lateinit var tvOwnerEmail: TextView
    lateinit var ivBackAF: ImageView
    lateinit var ivCall: ImageView
    lateinit var ivEmail: ImageView
    lateinit var pbAdoptFragment: ProgressBar
    var ownerDetails = UserModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewOfAdopt = inflater.inflate(R.layout.fragment_adopt, container, false)
        rcvAdoptPet = viewOfAdopt.findViewById(R.id.rcvAdoptPet)
        llContactUser = viewOfAdopt.findViewById(R.id.llContactUser)
        tvOwnerEmail = viewOfAdopt.findViewById(R.id.tvOwnerEmail)
        tvOwnerName = viewOfAdopt.findViewById(R.id.tvOwnerName)
        tvOwnerMobile = viewOfAdopt.findViewById(R.id.tvOwnerMobile)
        pbAdoptFragment = viewOfAdopt.findViewById(R.id.pbAdoptFragment)
        ivCall = viewOfAdopt.findViewById(R.id.ivCall)
        ivEmail = viewOfAdopt.findViewById(R.id.ivEmail)

        ivBackAF = viewOfAdopt.findViewById(R.id.ivBackAF)
        initialiseVariables()
        fetchPosts()
        ivBackAF.setOnClickListener{
            hideOwnerContact()
            showRCV()
        }
        return viewOfAdopt
    }

    private fun initialiseVariables() {
       adoptArrayModel = AdoptArrayModel()

    }

    private fun fetchPosts() {
        showProgressBar()
        FirebaseFirestore.getInstance().collection("adopt posts")
            .document("all posts")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    hideProgressBar()
                    if(it.result.exists()){
                        adoptArrayModel = it.result.toObject(AdoptArrayModel::class.java)!!
                        setPostsInRCV(adoptArrayModel.adoptPostsArray)
                        Log.i("adi", "posts  fetched successfully")
                    }
                    else{
                        Log.i("adi", "posts  result empty ")

                    }
                }
                else{
                    hideProgressBar()
                    Toast.makeText(parentFragment?.context ,"Error encountered.",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setPostsInRCV(adoptPostsArray: ArrayList<AdoptPostsModel>?) {
        rcvAdoptPet.layoutManager = LinearLayoutManager(parentFragment?.context)
        rcvAdoptPet.adapter = AdoptPetAdapter(adoptPostsArray!!, object : AdoptPetAdapter.Listener {


            override fun onPostClicked(position: Int) {
                Log.i("adi", "onPostClicked: showing details of post $position ")
            }

            override fun onContactUserClicked(userID: String) {
                var uid = userID.replace(" ","")
                Log.i("adi", "clicked $uid")


                FirebaseFirestore.getInstance().collection("Users")
                    .document(uid)
                    .get()
                    .addOnCompleteListener {
                        Log.i("adi", "completed fetch $userID")
                        if (it.isSuccessful) {
                            Log.i("adi", "success fetching $userID")
                            hideProgressBar()
                            Log.i("adi", "${it.result.toString()} ")
                            if (it.result.exists()) {
                                Log.i("adi", "result exists")
                                ownerDetails = it.result.toObject(UserModel::class.java)!!
                                Log.i("adi", "fetched username is ${ownerDetails.userName} ")
                                Log.i("adi", ownerDetails.contactNo)
                                setValuesOfOwner()
                                showOwnerContact()
                                hideRCV()

                            } else {
                                Log.i("adi", "result is empty")
                            }


                        } else {
                            hideProgressBar()
                            Toast.makeText(
                                parentFragment?.context,
                                "Error encountered.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            }
        })
    }

    private fun fetchUserDetails(uid:String) {

        Log.i("adi", "fetchUserDetails called")
        showProgressBar()
        Log.i("adi", "uid is $uid")



    }

    private fun setValuesOfOwner() {
        tvOwnerName.text = ownerDetails.userName
        tvOwnerMobile.text = ownerDetails.contactNo
        tvOwnerEmail.text = ownerDetails.email

        ivCall.setOnClickListener {
            val u: Uri = Uri.parse("tel:" + tvOwnerMobile.text)

            val i = Intent(Intent.ACTION_DIAL,u)
            try {
                startActivity(i)
            }
            catch(e:SecurityException) {
                Log.i("adi", "exception: ${e.message} ")
            }
        }
        ivEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this

            intent.putExtra(Intent.EXTRA_EMAIL, ownerDetails.email)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Want to adopt your pet.")

            startActivity(intent)

        }
    }

    private fun showRCV(){
        rcvAdoptPet.visibility = View.VISIBLE
    }
    private fun hideRCV(){
        rcvAdoptPet.visibility = View.GONE

    }
    private fun showOwnerContact(){
        llContactUser.visibility = View.VISIBLE
    }
    private fun hideOwnerContact(){
        llContactUser.visibility = View.GONE
    }
 private fun showProgressBar(){
        pbAdoptFragment.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        pbAdoptFragment.visibility = View.GONE
    }
//    private fun showDetails(){
//        llDetails.visibility = View.VISIBLE
//    }
//    private fun hideDetails(){
//        llDetails.visibility = View.GONE
//    }




}