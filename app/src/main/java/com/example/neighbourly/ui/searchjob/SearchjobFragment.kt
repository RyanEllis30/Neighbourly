package com.example.neighbourly.ui.searchjob

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Color.red
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neighbourly.DatabaseHelper
import com.example.neighbourly.R
import java.util.*

class SearchjobFragment : Fragment() {

    private lateinit var jobDetailsDialog: AlertDialog
    fun showCustomDialog(jobDescription: String, jobType: String, status: String, additionalRequirements: String, customerAccountId: String, jobId: String) {
        val inflater: LayoutInflater = this.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.activity_searchjob_details, null)

        val jobDescriptionText = dialogView.findViewById<TextView>(R.id.description)
        val jobTypeText = dialogView.findViewById<TextView>(R.id.type)
        val statusText = dialogView.findViewById<TextView>(R.id.status)
        val additionalRequirementsText = dialogView.findViewById<TextView>(R.id.requirements)
        val customerName = handler.getUsername()
        //val customerName = handler.findCustomerName(customerAccountId.toInt())
        val customerAccountNameText = dialogView.findViewById<TextView>(R.id.custID)

        jobDescriptionText.text = jobDescription
        jobTypeText.text = jobType
        statusText.text = status
        additionalRequirementsText.text = additionalRequirements
        customerAccountNameText.text = customerName

        val cancel: Button = dialogView.findViewById(R.id.cancelJobDetails)
        cancel.setOnClickListener {
            jobDetailsDialog.cancel()
        }

        val apply: Button = dialogView.findViewById(R.id.applyJobDetails)
        apply.setOnClickListener {
            handler.applyToJob(jobId, customerAccountId)
            Toast.makeText(requireContext(), "Application for job submitted", Toast.LENGTH_LONG).show()
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        handler = DatabaseHelper(requireContext())

        val root = inflater.inflate(R.layout.fragment_searchjob, container, false)
        var jobs = handler.findAllJobs()
        val jobCount = jobs.count()
        var i = 1


        //THIS DYNAMICALLY CREATES BUTTONS AND TEXTVIEWS FOR JOBS AND DETAILS
        while (i < jobCount) {
            val dynamicButton = Button(context)
            val dynamicText = TextView(context)
            val jobId = i

            val data = handler.findJobDetails(jobId.toString())

            dynamicButton.text = "Details"
            dynamicText.text = jobs[i-1]
            dynamicButton.id = i

            // Styling
            dynamicButton.height = 150
            dynamicText.height = 150
            dynamicText.setTextAppearance(R.style.TextAppearance_AppCompat_Caption)
            dynamicButton.setTextAppearance(R.style.TextAppearance_AppCompat_Button)
            dynamicText.textSize = 28F

            dynamicButton.setOnClickListener {
                showCustomDialog(data[0], data[1], data[2], data[3], data[4], jobId.toString())
            }

            val buttonLayout = root.findViewById<LinearLayout>(R.id.linearLayout3)
            val textLayout = root.findViewById<LinearLayout>(R.id.linearLayout2)
            buttonLayout.addView(dynamicButton)
            textLayout.addView(dynamicText)
            i++
        }

        return root

    }
}