package com.example.straycaregsc.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Models.AdoptPostsModel
import com.example.straycaregsc.R
import com.squareup.picasso.Picasso

class AdoptPetAdapter(private val postModelArray: ArrayList<AdoptPostsModel>, val listener:AdoptPetAdapter.Listener):RecyclerView.Adapter<AdoptPetAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvPostTitleAP:TextView
        val tvUserNameAP:TextView
        val ivPostAP :ImageView
        val llPostLayout:LinearLayout
        val llContactOwner:LinearLayout
        val llDetails:LinearLayout
        val tvLocation:TextView
        val tvLocationAPI:TextView
        val tvBreedAPI:TextView
        val tvDetailsAPI:TextView
        val tvSpConsiderationsAPI:TextView
        init {
            tvPostTitleAP = view.findViewById(R.id.tvPostTitleAP)
            ivPostAP = view.findViewById(R.id.ivPostAP)
            llPostLayout = view.findViewById(R.id.llPostLayout)
            tvUserNameAP = view.findViewById(R.id.tvUserNameAP)
            tvLocation = view.findViewById(R.id.tvLocationAP)
            llContactOwner = view.findViewById(R.id.llContactOwner)
            llDetails = view.findViewById(R.id.llDetails)
            tvLocationAPI = view.findViewById(R.id.tvLocationAPI)
            tvBreedAPI = view.findViewById(R.id.tvBreedAPI)
            tvSpConsiderationsAPI = view.findViewById(R.id.tvSpConsiderationsAPI)
            tvDetailsAPI = view.findViewById(R.id.tvDetailsOfPetAPI)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adopt_pet,parent,false)
        return ViewHolder(view)
    }

    public interface Listener{
        fun onPostClicked(position: Int)
        fun onContactUserClicked(userID: String)
    }
    override fun getItemCount(): Int {
        return postModelArray.size
    }
    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0f
        ) // toYDelta
        animate.setDuration(500)
        animate.setFillAfter(true)
        view.startAnimation(animate)
    }

    // slide the view from its current position to below itself
    fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            0f,  // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.setDuration(500)
        animate.setFillAfter(true)
        view.startAnimation(animate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUserNameAP.text = postModelArray[position].userName
        holder.tvPostTitleAP.text = postModelArray[position].caption
        holder.tvLocation.text = postModelArray[position].location
        Picasso.get()
            .load(postModelArray[position].imageUrl)
            .into(holder.ivPostAP)
        holder.llContactOwner.setOnClickListener{
            listener.onContactUserClicked(postModelArray[position].userID)
            Log.i("adi", "onBindViewHolder:${postModelArray[position].userID}")
        }
        holder.tvBreedAPI.text = postModelArray[position].breed
        holder.tvLocationAPI.text= postModelArray[position].location
        holder.tvDetailsAPI.text= postModelArray[position].details
        holder.tvSpConsiderationsAPI.text = postModelArray[position].considerations

        var click = 0
        holder.llPostLayout.setOnClickListener{
            click++
            if(click%2==0){
                holder.llDetails.visibility = View.GONE
//                slideUp( holder.llDetails)


            }
            else{
                holder.llDetails.visibility = View.VISIBLE
            }


            listener.onPostClicked(position)
        }
    }
}