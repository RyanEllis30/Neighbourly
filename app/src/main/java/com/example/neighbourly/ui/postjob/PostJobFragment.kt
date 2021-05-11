package com.example.neighbourly.ui.postjob

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
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
import com.example.neighbourly.DatabaseHelper
import com.example.neighbourly.R
import java.util.*

class PostJobFragment : Fragment() {

    private lateinit var addjobDialog: AlertDialog
    fun showCustomDialog() {
        val inflater: LayoutInflater = this.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.activity_newjob_popup, null)

        val cancel: Button = dialogView.findViewById(R.id.Canceljob)
        cancel.setOnClickListener {
            addjobDialog.cancel()
        }
        val add: Button = dialogView.findViewById(R.id.addJob)
        add.setOnClickListener {
            val description = dialogView.findViewById<TextView>(R.id.editnewdescription).text
            val jobType = dialogView.findViewById<TextView>(R.id.editnewjobtype).text
            val requirements = dialogView.findViewById<TextView>(R.id.editnewrequirements).text
            handler.createJob(description.toString(), jobType.toString(), "New", requirements.toString(), "0")
            Toast.makeText(requireContext(), "Creating Job", Toast.LENGTH_LONG).show()
            addjobDialog.cancel()
            //Need to refresh the page here I think
        }
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(arg0: DialogInterface) {

            }
        })
        dialogBuilder.setView(dialogView)

        addjobDialog = dialogBuilder.create();
        //alertDialog.window!!.getAttributes().windowAnimations = R.style.PauseDialogAnimation
        addjobDialog.show()
    }

    private lateinit var jobDetailsDialog: AlertDialog
    fun showDetailsDialog(jobDescription: String, jobType: String, status: String, additionalRequirements: String, customerAccountId: String, jobId: String) {
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
        apply.visibility = View.GONE

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

    lateinit var handler: DatabaseHelper

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        handler = DatabaseHelper(requireContext())
        val root = inflater.inflate(R.layout.fragment_postjob, container, false)
        val postJobButton = root.findViewById<Button>(R.id.button2)

        postJobButton.setOnClickListener { // This is the submit button
            if (this::handler.isInitialized) { // This is the submit button
                showCustomDialog()

            }
        }

        var jobs = handler.findUsersJobIDs()
        val jobCount = jobs.count()
        var i = 1

        //THIS DYNAMICALLY CREATES BUTTONS AND TEXTVIEWS FOR JOBS AND DETAILS
        while (i < jobCount) {
            val dynamicButton = Button(context)
            val dynamicText = TextView(context)
            val jobId = jobs[i-1]

            val data = handler.findJobDetails(jobId)

            dynamicButton.text = "Details"
            dynamicText.text = data[0]
            dynamicButton.id = jobId.toInt()

            // Styling
            dynamicButton.height = 150
            dynamicText.height = 150
            dynamicText.textSize = 28F

            dynamicButton.setOnClickListener {
                showDetailsDialog(data[0], data[1], data[2], data[3], data[4], jobId.toString())
            }

            val buttonLayout = root.findViewById<LinearLayout>(R.id.linearLayout12)
            val textLayout = root.findViewById<LinearLayout>(R.id.linearLayout11)
            buttonLayout.addView(dynamicButton)
            textLayout.addView(dynamicText)
            i++
        }
        return root
    }
}