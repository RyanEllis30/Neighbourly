package com.example.neighbourly.ui.yourjobs

import androidx.fragment.app.Fragment
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.neighbourly.R
import com.example.neighbourly.ui.postjob.PostJobFragment
import com.example.neighbourly.ui.searchjob.SearchjobFragment

class yourJobsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_yourjobs, container, false)

        return root
    }
}