package com.example.neighbourly.ui.contact

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.example.neighbourly.DatabaseHelper
import com.example.neighbourly.Navigation
import com.example.neighbourly.R
import com.example.neighbourly.ui.home.HomepageFragment

class ContactFragment : Fragment() {

    lateinit var handler: DatabaseHelper

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        handler = DatabaseHelper(requireContext())

        val root = inflater.inflate(R.layout.fragment_contact, container, false)

        val contactMessage = root.findViewById<EditText>(R.id.TextInputEditTextMessageContactUS)
        val contactEmail = root.findViewById<EditText>(R.id.editTextTextEmailAddress)
        var email = handler.getEmail()
        contactEmail.text = email.toEditable()
        val submitContactUsButton = root.findViewById<Button>(R.id.buttonSubmitContactUs)

        submitContactUsButton.setOnClickListener {//This is the submit button, when pressed will run this script
            val message = contactMessage.text
            val email = contactEmail.text
            if (this::handler.isInitialized) {
                handler.insertContactMessage("$email", "$message")
                Toast.makeText(requireContext(), "Your feedback has been noted.", Toast.LENGTH_LONG).show()
                val intent = Intent(context, Navigation::class.java).apply {}
                startActivity(intent)
            }
        }

        return root
    }
}