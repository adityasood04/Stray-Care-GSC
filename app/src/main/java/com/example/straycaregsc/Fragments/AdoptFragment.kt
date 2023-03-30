package com.example.straycaregsc.Fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Adapters.AdoptPetAdapter
import com.example.straycaregsc.AdoptArrayModel
import com.example.straycaregsc.AdoptPostsModel
import com.example.straycaregsc.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class AdoptFragment : Fragment() {
    lateinit var adoptArrayModel: AdoptArrayModel
    lateinit var rcvAdoptPet: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewOfAdopt = inflater.inflate(R.layout.fragment_adopt, container, false)
        rcvAdoptPet = viewOfAdopt.findViewById(R.id.rcvAdoptPet)
        initialiseVariables()
        fetchPosts()
        return viewOfAdopt
    }

    private fun initialiseVariables() {
       adoptArrayModel = AdoptArrayModel()


    }

    private fun fetchPosts() {
        FirebaseFirestore.getInstance().collection("adopt posts")
            .document("all posts")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
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
                    Toast.makeText(parentFragment?.context ,"Error encountered.",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setPostsInRCV(adoptPostsArray: ArrayList<AdoptPostsModel>?) {
        rcvAdoptPet.layoutManager = LinearLayoutManager(parentFragment?.context)
        rcvAdoptPet.adapter = AdoptPetAdapter(adoptPostsArray!!)
    }


}