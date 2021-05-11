package com.example.neighbourly.ui.postjob

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
            Toast.makeText(requireContext(), "Creating Job", Toast.LENGTH_SHORT).show()
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

    lateinit var handler: DatabaseHelper

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
        return root
    }
}