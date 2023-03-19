package com.example.straycaregsc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.straycaregsc.R


class PostsAdapter(private val namesList:ArrayList<String>, val listener:Listener):RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tvUserName:TextView
        var ivShare:ImageView
        init {
            tvUserName = view.findViewById(R.id.tvUserName)
            ivShare = view.findViewById(R.id.ivShare)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return ViewHolder(view)
    }

    public interface Listener{
        fun shareClicked(name:String)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.tvUserName.text = namesList.get(position)
        holder.ivShare.setOnClickListener(View.OnClickListener {
            listener.shareClicked(namesList[position])
        })
    }

    override fun getItemCount(): Int {
        return  namesList.size
      }


}