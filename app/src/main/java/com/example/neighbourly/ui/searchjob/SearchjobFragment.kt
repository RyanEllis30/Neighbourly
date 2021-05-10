package com.example.neighbourly.ui.searchjob

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

class SearchjobFragment : Fragment() {

    private lateinit var searchjobViewModel: SearchjobViewModel
    lateinit var handler: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        handler = DatabaseHelper(requireContext())

        val root = inflater.inflate(R.layout.fragment_searchjob, container, false)
        var jobs = handler.findAllJobs()

        val paintFenceDetailsButton = root.findViewById<Button>(R.id.button7)
        val textView3: TextView = root.findViewById(R.id.textView3) as TextView
        textView3.text = jobs[0]

        paintFenceDetailsButton.setOnClickListener { //This is the first details button
            val jobId = "1"

            if (this::handler.isInitialized) {
                val data = handler.findJobDetails("$jobId")

                Log.d("Errors", "Line 44 Searchjob $data")
                val jobDescription = data[0]
                val jobType = data[1]
                val status = data[2]
                val additionalRequirements = data[3]
                val customerAccountId = data[4]

                Toast.makeText(requireContext(), "Description: $jobDescription \njobType: $jobType \nstatus: $status \nadditionalRequirements: $additionalRequirements \ncustomerAccountId: $customerAccountId \n", Toast.LENGTH_SHORT).show()
            }
        }

        val shoppingDetailsButton = root.findViewById<Button>(R.id.button8)
        val textView4: TextView = root.findViewById(R.id.textView4) as TextView
        textView4.text = jobs[1]
        shoppingDetailsButton.setOnClickListener { // This is the second details button
            val jobId = "2"

            if (this::handler.isInitialized) {
                val data = handler.findJobDetails("$jobId")

                val jobDescription = data[0]
                val jobType = data[1]
                val status = data[2]
                val additionalRequirements = data[3]
                val customerAccountId = data[4]
                textView4.text = jobDescription

                Toast.makeText(requireContext(), "Description: $jobDescription \njobType: $jobType \nstatus: $status \nadditionalRequirements: $additionalRequirements \ncustomerAccountId: $customerAccountId \n", Toast.LENGTH_SHORT).show()
            }
        }

        return root

    }
}