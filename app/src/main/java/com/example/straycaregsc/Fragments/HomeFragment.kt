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
    var namesArrayTest:ArrayList<String> = arrayListOf("Aditya", "Shrestha","Vatsalya","Adarsh")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewOfLayout = inflater.inflate(R.layout.fragment_home, container, false)
        rcvPostsHF =viewOfLayout.findViewById(R.id.rcvPostsHF)
        fetchPosts()
        setPostsRCV()








        return viewOfLayout


    }

    private fun setPostsRCV() {
        rcvPostsHF.layoutManager = LinearLayoutManager(parentFragment?.context)
        rcvPostsHF.adapter = PostsAdapter(postsArray,object:PostsAdapter.Listener{
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

        FirebaseFirestore.getInstance().collection("posts").document()
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    if(it.result.exists()){
                        val post = it.result.toObject(PostModel::class.java)
                        postsArray.add(post!!)
                        Log.i("adi", "post fetched is ${post.caption}")

                    }
                }
            }

    }
}