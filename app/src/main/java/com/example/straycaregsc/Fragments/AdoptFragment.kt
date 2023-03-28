package com.example.straycaregsc.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Adapters.AdoptPetAdapter
import com.example.straycaregsc.AdoptArrayModel
import com.example.straycaregsc.AdoptPostsModel
import com.example.straycaregsc.R

class AdoptFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val namesArray = arrayListOf<String>("Aditya", "Vatsalya", "Shrestha","Adarsh")
        val viewOfAdopt = inflater.inflate(R.layout.fragment_adopt, container, false)
        val rcvAdoptPet:RecyclerView = viewOfAdopt.findViewById(R.id.rcvAdoptPet)
        rcvAdoptPet.layoutManager = LinearLayoutManager(parentFragment?.context)
        rcvAdoptPet.adapter = AdoptPetAdapter(namesArray)
        return viewOfAdopt
    }



}