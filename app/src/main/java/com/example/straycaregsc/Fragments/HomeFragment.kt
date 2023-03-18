package com.app.straycare.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.straycare.Adapters.PostsAdapter
import com.example.straycaregsc.R

class HomeFragment : Fragment() {

    lateinit var rcvPostsHF :RecyclerView
    var namesArrayTest:ArrayList<String> = arrayListOf("Aditya", "Shreshtha","Vatsalya","Adarsh")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewOfLayout = inflater.inflate(R.layout.fragment_home, container, false)
        rcvPostsHF =viewOfLayout.findViewById(R.id.rcvPostsHF)
        rcvPostsHF.layoutManager = LinearLayoutManager(parentFragment?.context)
        rcvPostsHF.adapter = PostsAdapter(namesArrayTest)
        return viewOfLayout


    }
}