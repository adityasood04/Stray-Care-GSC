package com.example.straycaregsc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.straycaregsc.PostModel
import com.example.straycaregsc.R


class PostsAdapter(private val postsArray:ArrayList<PostModel>, val listener:Listener):RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tvUserName:TextView
        var ivShare:ImageView
        var tvPostTitle:TextView
        init {
            tvUserName = view.findViewById(R.id.tvUserName)
            ivShare = view.findViewById(R.id.ivShare)
            tvPostTitle = view.findViewById(R.id.tvPostTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return ViewHolder(view)
    }

    public interface Listener{
        fun shareClicked(caption:String)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPostTitle.text = postsArray[position].caption
    }

    override fun getItemCount(): Int {
        return  postsArray.size
      }


}