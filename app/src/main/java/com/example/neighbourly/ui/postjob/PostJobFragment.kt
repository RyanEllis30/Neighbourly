package com.example.neighbourly.ui.postjob

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neighbourly.DatabaseHelper
import com.example.neighbourly.R
import java.util.*

class PostJobFragment : Fragment() {

    lateinit var handler: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        handler = DatabaseHelper(requireContext())

        val root = inflater.inflate(R.layout.fragment_postjob, container, false)

        val postJobSubmitButton = root.findViewById<Button>(R.id.button2)

        postJobSubmitButton.setOnClickListener { // This is the submit button
            val jobDescription = "jobDescription"
            val jobType = "jobType"
            val status = "status"
            val additionalRequirements = "additionalRequirements"
            val customerAccountId = "customerAccountId"
            val workerAccountId = "workerAccountId"

            if (this::handler.isInitialized) {
                handler.createJob("$jobDescription", "$jobType", "$status", "$additionalRequirements", "$customerAccountId", "$workerAccountId")
                Log.d("Errors", "Line 43 postjob")
                Toast.makeText(requireContext(), "Creating Job", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }
}