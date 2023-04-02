package com.example.straycaregsc.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Models.GlobalPostsModel
import com.example.straycaregsc.Models.PostModel
import com.example.straycaregsc.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class PostsAdapter(private val postsArray:ArrayList<PostModel>, val listener:Listener):RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    var posts:GlobalPostsModel = GlobalPostsModel()
     var likes: Int =0

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tvUserName:TextView
        var ivShare:ImageView
        var tvPostTitle:TextView
        var ivPost:ImageView
        var ivLike:ImageView
        var ivUserImage:CircleImageView
        init {
            tvUserName = view.findViewById(R.id.tvUserName)
            ivShare = view.findViewById(R.id.ivShare)
            tvPostTitle = view.findViewById(R.id.tvPostTitle)
            ivPost = view.findViewById(R.id.ivPost)
            ivLike = view.findViewById(R.id.ivLike)
            ivUserImage = view.findViewById(R.id.ivUserImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return ViewHolder(view)
    }

    public interface Listener{
        fun shareClicked(caption:String)
        fun likeClicked(position:Int,likes:Int)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i =0
        holder.tvPostTitle.text = postsArray[position].caption
        holder.tvUserName.text = postsArray[position].user
        if(!postsArray[position].userDp.isNullOrBlank()){

            Picasso.get().load(postsArray[position].userDp).into(holder.ivUserImage)
        }
        else{
            holder.ivUserImage.setImageResource(R.drawable.user)
        }
        holder.ivShare.setOnClickListener{
            listener.shareClicked("${postsArray[position].caption} - by ${postsArray[position].user}")
        }
        var likes =0
        holder.ivLike.setOnClickListener{
              likes++
            listener.likeClicked(position,likes)
        }
        Picasso.get().load(postsArray[position].imageUrl).into(holder.ivPost)

    }

//    private fun fetchPosts(position: Int) {
//        FirebaseFirestore.getInstance().collection("posts").document("global posts")
//            .get()
//            .addOnCompleteListener{
//                if(it.isSuccessful){
//                    Log.i("adi", "post fetched is successfull")
//                    if(it.result.exists()){
//                        Log.i("adi", "post fetched result exists")
//
//                        try {
//                            posts= it.result.toObject(GlobalPostsModel::class.java)!!
//                            likes = posts.postsArray[position].likes
//                            likes++
//                        }
//                        catch (e:Exception){
//                            Log.i("adi", "error:${e}")
//                        }
//                    }
//                    else{
//                        Log.i("adi", "no result exists")
//                    }
//                }
//                else{
//                    Log.i("adi", "not able to fetch posts")
//
//                }
//            }
//    }

    override fun getItemCount(): Int {
        return  postsArray.size
      }


}