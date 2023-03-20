package com.example.straycaregsc.Adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Fragments.AdoptFragment
import com.example.straycaregsc.R

class AdoptPetAdapter(val userNamesArray:ArrayList<String>):RecyclerView.Adapter<AdoptPetAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvPostTitleAP:TextView
        val tvUserNameAP:TextView
        val ivPostAP :ImageView
        val llPostItemAP:LinearLayout
        init {
            tvPostTitleAP = view.findViewById(R.id.tvPostTitleAP)
            ivPostAP = view.findViewById(R.id.ivPostAP)
            llPostItemAP = view.findViewById(R.id.llPostItemAP)
            tvUserNameAP = view.findViewById(R.id.tvUserNameAP)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adopt_pet,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userNamesArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUserNameAP.text = userNamesArray[position]
    }
}