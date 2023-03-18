package com.app.straycare.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.straycaregsc.R


class PostsAdapter(private val namesList:ArrayList<String>):RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tvUserName:TextView
        init {
            tvUserName = view.findViewById(R.id.tvUserName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.tvUserName.text = namesList.get(position)
    }

    override fun getItemCount(): Int {
        return  namesList.size
      }


}