package com.example.straycaregsc.Fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Adapters.PostsAdapter
import com.example.straycaregsc.PostModel
import com.example.straycaregsc.R
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    lateinit var rcvPostsHF :RecyclerView
    lateinit var post :PostModel
    lateinit var postsArray :ArrayList<PostModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private fun initialiseVariables() {
        postsArray = ArrayList()
        post = PostModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewOfLayout = inflater.inflate(R.layout.fragment_home, container, false)
        initialiseVariables()
        rcvPostsHF =viewOfLayout.findViewById(R.id.rcvPostsHF)
        fetchPosts()


        return viewOfLayout


    }

    private fun setPostsRCV(postArray:ArrayList<PostModel>) {
        rcvPostsHF.layoutManager = LinearLayoutManager(parentFragment?.context)
        rcvPostsHF.adapter = PostsAdapter(postArray,object:PostsAdapter.Listener{
            override fun shareClicked(caption: String) {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Sharing post from $caption in StrayCare App." )
                startActivity(Intent.createChooser(shareIntent,"Share via"))
            }
        })
    }

    fun fetchPosts(){
        Log.i("adi", "post fetched is called")


        FirebaseFirestore.getInstance().collection("posts").document("global posts")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    Log.i("adi", "post fetched is successfull")
                    if(it.result.exists()){
                        Log.i("adi", "post fetched result exists")
                        postsArray = it.result.toObject(ArrayList<PostModel>()::class.java)!!
                        setPostsRCV(postsArray)
                    }
                    else{
                        Log.i("adi", "no result exists")

                    }
                }
                else{
                    Log.i("adi", "not able to fetch posts")

                }
            }
    }
}
