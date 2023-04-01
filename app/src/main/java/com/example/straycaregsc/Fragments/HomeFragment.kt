package com.example.straycaregsc.Fragments


import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Adapters.PostsAdapter
import com.example.straycaregsc.Models.GlobalPostsModel
import com.example.straycaregsc.Models.PostModel
import com.example.straycaregsc.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    lateinit var rcvPostsHF :RecyclerView
    lateinit var posts : GlobalPostsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun initialiseVariables() {
        posts = GlobalPostsModel()
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
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Please checkout this post on our app: ${caption}" )
                startActivity(Intent.createChooser(shareIntent,"Share via"))
            }
            override fun likeClicked(position:Int,likes:Int) {
                fetchPosts()
                posts.postsArray[position].likes = posts.postsArray[position].likes + likes
                Snackbar.make(rcvPostsHF,"Liked Successfully",Snackbar.LENGTH_SHORT).show()
                updatePosts(posts)
            }
        })
    }
    private fun updatePosts(posts: GlobalPostsModel){
        FirebaseFirestore.getInstance().collection("posts")
            .document("global posts")
            .set(posts)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Log.i("adi", "liked ")
                }
                else{
                    Log.i("adi", "error ")

                }
            }

    }

    private fun fetchPosts(){
        Log.i("adi", "post fetched is called")


        FirebaseFirestore.getInstance().collection("posts").document("global posts")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    Log.i("adi", "post fetched is successfull")
                    if(it.result.exists()){
                        Log.i("adi", "post fetched result exists")

                        try {
                             posts= it.result.toObject(GlobalPostsModel::class.java)!!
                            setPostsRCV(posts.postsArray)
                        }
                        catch (e:Exception){
                            Log.i("adi", "error:${e}")
                        }
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
