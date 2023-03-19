package com.example.straycaregsc.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straycaregsc.Adapters.PostsAdapter
import com.example.straycaregsc.R

class HomeFragment : Fragment() {

    lateinit var rcvPostsHF :RecyclerView
    var namesArrayTest:ArrayList<String> = arrayListOf("Aditya", "Shrestha","Vatsalya","Adarsh")

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
        rcvPostsHF.adapter = PostsAdapter(namesArrayTest,object:PostsAdapter.Listener{
            override fun shareClicked(name: String) {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.type="text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Sharing post from $name in StrayCare App." )
                    startActivity(Intent.createChooser(shareIntent,"Share via"))
            }

        })
        return viewOfLayout


    }
}