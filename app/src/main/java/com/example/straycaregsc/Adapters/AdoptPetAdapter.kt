package com.example.straycaregsc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.AdoptPostsModel
import com.example.straycaregsc.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdoptPetAdapter(private val postModelArray: ArrayList<AdoptPostsModel>):RecyclerView.Adapter<AdoptPetAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvPostTitleAP:TextView
        val tvUserNameAP:TextView
        val ivPostAP :ImageView
        val llPostItemAP:LinearLayout
        val tvLocation:TextView
        init {
            tvPostTitleAP = view.findViewById(R.id.tvPostTitleAP)
            ivPostAP = view.findViewById(R.id.ivPostAP)
            llPostItemAP = view.findViewById(R.id.llPostItemAP)
            tvUserNameAP = view.findViewById(R.id.tvUserNameAP)
            tvLocation = view.findViewById(R.id.tvLocationAP)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adopt_pet,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postModelArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUserNameAP.text = postModelArray[position].userName
        holder.tvPostTitleAP.text = postModelArray[position].caption
        holder.tvLocation.text = postModelArray[position].location
        Picasso.get()
            .load(postModelArray[position].imageUrl)
            .into(holder.ivPostAP)
    }
}