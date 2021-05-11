package com.example.neighbourly.ui.home

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neighbourly.Navigation
import com.example.neighbourly.R
import com.example.neighbourly.ui.login.usernameKey
import com.example.neighbourly.ui.postjob.PostJobFragment
import com.example.neighbourly.ui.searchjob.SearchjobFragment
import com.example.neighbourly.ui.searchjob.searchjobDetails

class HomepageFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_homepage, container, false)
        val search: Button = root.findViewById(R.id.button3)
        val post: Button = root.findViewById(R.id.button4)

        search.setOnClickListener{
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(requireParentFragment().id, SearchjobFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        post.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(requireParentFragment().id, PostJobFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
        return root
    }
}
