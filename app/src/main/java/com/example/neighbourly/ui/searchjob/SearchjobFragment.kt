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

        val paintFenceDetailsButton = root.findViewById<Button>(R.id.button7)

        val textView3: TextView = root.findViewById(R.id.textView3) as TextView
        textView3.setOnClickListener {
            textView3.text = getString(R.string.paint_fence)
        }

        paintFenceDetailsButton.setOnClickListener { //This is the first details button
            val jobId = "1"

            if (this::handler.isInitialized) {
                val data = handler.findJobDetails("$jobId")
                val normalData = data.contentToString()
                Log.d("Errors", "Line 44 Searchjob $normalData")
                if (data.count() > 0) {
                    val jobDescription = normalData[0]
                    val jobType = normalData[1]
                    val status = normalData[2]
                    val additionalRequirements = normalData[3]
                    val customerAccountId = normalData[4]
                }

                Toast.makeText(requireContext(), "HELLO", Toast.LENGTH_SHORT).show()
            }
        }

        return root

    }
}