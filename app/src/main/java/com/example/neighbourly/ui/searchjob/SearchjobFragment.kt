package com.example.neighbourly.ui.searchjob

import android.app.AlertDialog
import android.content.DialogInterface
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

    private lateinit var jobDetailsDialog: AlertDialog
    fun showCustomDialog(jobDescription: String, jobType: String, status: String, additionalRequirements: String, customerAccountId: String) {
        val inflater: LayoutInflater = this.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.activity_searchjob_details, null)

        val jobDescriptionText = dialogView.findViewById<TextView>(R.id.description)
        val jobTypeText = dialogView.findViewById<TextView>(R.id.type)
        val statusText = dialogView.findViewById<TextView>(R.id.status)
        val additionalRequirementsText = dialogView.findViewById<TextView>(R.id.requirements)
        val customerName = "Jason"
        //val customerName = handler.findCustomerName(customerAccountId.toInt())
        val customerAccountNameText = dialogView.findViewById<TextView>(R.id.custID)

        jobDescriptionText.text = jobDescription
        jobTypeText.text = jobType
        statusText.text = status
        additionalRequirementsText.text = additionalRequirements
        customerAccountNameText.text = customerName

        val cancel: Button = dialogView.findViewById(R.id.CancelJobDetails)
        cancel.setOnClickListener {
            jobDetailsDialog.cancel()
        }

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(arg0: DialogInterface) {

            }
        })
        dialogBuilder.setView(dialogView)

        jobDetailsDialog = dialogBuilder.create();
        //alertDialog.window!!.getAttributes().windowAnimations = R.style.PauseDialogAnimation
        jobDetailsDialog.show()
    }


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
        if (jobs.size > 0) {
            textView3.text = jobs[0]
        }
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

                showCustomDialog(jobDescription, jobType, status, additionalRequirements, customerAccountId)
            }
        }

        val shoppingDetailsButton = root.findViewById<Button>(R.id.button8)
        val textView4: TextView = root.findViewById(R.id.textView4) as TextView
        if (jobs.size > 1) {
            textView4.text = jobs[1]
        }
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

                showCustomDialog(jobDescription, jobType, status, additionalRequirements, customerAccountId)
            }
        }

        val washCarDetailsButton = root.findViewById<Button>(R.id.button9)
        val textView5: TextView = root.findViewById(R.id.textView5) as TextView
        if (jobs.size > 2) {
            textView5.text = jobs[2]
        }
        washCarDetailsButton.setOnClickListener { // This is the second details button
            val jobId = "3"

            if (this::handler.isInitialized) {
                val data = handler.findJobDetails("$jobId")

                val jobDescription = data[0]
                val jobType = data[1]
                val status = data[2]
                val additionalRequirements = data[3]
                val customerAccountId = data[4]
                textView5.text = jobDescription

                showCustomDialog(jobDescription, jobType, status, additionalRequirements, customerAccountId)
            }
        }

        val gardeningDetailsButton = root.findViewById<Button>(R.id.button10)
        val textView6: TextView = root.findViewById(R.id.textView6) as TextView
        if (jobs.size > 3) {
            textView6.text = jobs[3]
        }
        gardeningDetailsButton.setOnClickListener { // This is the second details button
            val jobId = "4"

            if (this::handler.isInitialized) {
                val data = handler.findJobDetails("$jobId")

                val jobDescription = data[0]
                val jobType = data[1]
                val status = data[2]
                val additionalRequirements = data[3]
                val customerAccountId = data[4]
                textView6.text = jobDescription

                showCustomDialog(jobDescription, jobType, status, additionalRequirements, customerAccountId)
            }
        }

        val shoppingDetailsButton2 = root.findViewById<Button>(R.id.button11)
        val textView7: TextView = root.findViewById(R.id.textView7) as TextView
        if (jobs.size > 4) {
            textView7.text = jobs[4]
        }
        shoppingDetailsButton2.setOnClickListener { // This is the second details button
            val jobId = "5"

            if (this::handler.isInitialized) {
                val data = handler.findJobDetails("$jobId")

                val jobDescription = data[0]
                val jobType = data[1]
                val status = data[2]
                val additionalRequirements = data[3]
                val customerAccountId = data[4]
                textView7.text = jobDescription

                showCustomDialog(jobDescription, jobType, status, additionalRequirements, customerAccountId)
            }
        }

        val dogWalkingDetailsButton = root.findViewById<Button>(R.id.button12)
        val textView8: TextView = root.findViewById(R.id.textView8) as TextView
        if (jobs.size > 5) {
            textView8.text = jobs[5]
        }
        dogWalkingDetailsButton.setOnClickListener { // This is the second details button
            val jobId = "6"

            if (this::handler.isInitialized) {
                val data = handler.findJobDetails("$jobId")

                val jobDescription = data[0]
                val jobType = data[1]
                val status = data[2]
                val additionalRequirements = data[3]
                val customerAccountId = data[4]
                textView8.text = jobDescription

                showCustomDialog(jobDescription, jobType, status, additionalRequirements, customerAccountId)
            }
        }

        val gardeningDetailsButton2 = root.findViewById<Button>(R.id.button13)
        val textView9: TextView = root.findViewById(R.id.textView9) as TextView
        if (jobs.size > 6) {
            textView9.text = jobs[6]
        }
        gardeningDetailsButton2.setOnClickListener { // This is the second details button
            val jobId = "7"

            if (this::handler.isInitialized) {
                val data = handler.findJobDetails("$jobId")

                val jobDescription = data[0]
                val jobType = data[1]
                val status = data[2]
                val additionalRequirements = data[3]
                val customerAccountId = data[4]
                textView9.text = jobDescription

                showCustomDialog(jobDescription, jobType, status, additionalRequirements, customerAccountId)
            }
        }

        return root

    }
}