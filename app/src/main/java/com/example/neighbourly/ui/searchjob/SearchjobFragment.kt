package com.example.neighbourly.ui.searchjob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neighbourly.R

class SearchjobFragment : Fragment() {

    private lateinit var searchjobViewModel: SearchjobViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchjobViewModel =
            ViewModelProvider(this).get(SearchjobViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_searchjob, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        searchjobViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}