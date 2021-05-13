package com.example.neighbourly.ui.account

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.neighbourly.DatabaseHelper
import com.example.neighbourly.R

class AccountFragment : Fragment() {

    private lateinit var changeAddressDialog: AlertDialog
    lateinit var handler:DatabaseHelper

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun showCustomDialog() {
        val inflater: LayoutInflater = this.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.activity_address_popup, null)

        val cancel: Button = dialogView.findViewById(R.id.CancelAddress)
        cancel.setOnClickListener {
            changeAddressDialog.cancel()
        }

        val update: Button = dialogView.findViewById(R.id.updateAddress)
        update.setOnClickListener {
            // add code to update the address
            val houseNum = changeAddressDialog.findViewById<EditText>(R.id.edithousenumber)
            val street = changeAddressDialog.findViewById<EditText>(R.id.editstreet)
            val city = changeAddressDialog.findViewById<EditText>(R.id.editCity)
            val postcode = changeAddressDialog.findViewById<EditText>(R.id.editpostcode)

            handler.submitAddressData(houseNum.text.toString(), street.text.toString(), city.text.toString(), postcode.text.toString())
            Toast.makeText(requireContext(), "Address Changed", Toast.LENGTH_LONG).show()
            changeAddressDialog.cancel()
        }
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(arg0: DialogInterface) {

            }
        })
        dialogBuilder.setView(dialogView)

        changeAddressDialog = dialogBuilder.create();
        changeAddressDialog.show()

        val houseNum = changeAddressDialog.findViewById<EditText>(R.id.edithousenumber)
        val street = changeAddressDialog.findViewById<EditText>(R.id.editstreet)
        val city = changeAddressDialog.findViewById<EditText>(R.id.editCity)
        val postcode = changeAddressDialog.findViewById<EditText>(R.id.editpostcode)

        val newAddressData = handler.getAddressData()
        println(newAddressData)
        houseNum.setText(newAddressData.getAsString("HouseNum"))
        street.setText(newAddressData.getAsString("Road"))
        city.setText(newAddressData.getAsString("City"))
        postcode.setText(newAddressData.getAsString("Postcode"))

    }

    private lateinit var changePasswordDialog: AlertDialog
    fun showCustomDialog2() {
        val inflater2: LayoutInflater = this.getLayoutInflater()
        val dialogView2: View = inflater2.inflate(R.layout.activity_password_popup, null)

        val cancel2: Button = dialogView2.findViewById(R.id.CancelPassword)
        cancel2.setOnClickListener {
            changePasswordDialog.cancel()
        }
        val update2: Button = dialogView2.findViewById(R.id.updatePassword)
        update2.setOnClickListener {
            // add code to update the password
            val oldPassword = changePasswordDialog.findViewById<EditText>(R.id.editoldpassword).text.toString()
            val newPassword = changePasswordDialog.findViewById<EditText>(R.id.editnewpassword).text.toString()
            if(handler.updatePassword(oldPassword, newPassword)){
                println("Success")
                Toast.makeText(requireContext(), "Password Changed", Toast.LENGTH_LONG).show()
                changePasswordDialog.cancel()
            }
            else{
                println("Task failed")
                Toast.makeText(requireContext(), "Task failed", Toast.LENGTH_LONG).show()
            }

        }
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(arg0: DialogInterface) {

            }
        })
        dialogBuilder.setView(dialogView2)

        changePasswordDialog = dialogBuilder.create();
        changePasswordDialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_account, container, false)

        val nameText = root.findViewById<EditText>(R.id.textView5)
        val emailText = root.findViewById<EditText>(R.id.editTextTextPersonName10)

        val nameButton = root.findViewById<Button>(R.id.editName)
        val emailButton = root.findViewById<Button>(R.id.button)

        if (this::handler.isInitialized) {
            nameText.text = handler.getUsername().toEditable()
            emailText.text = handler.getEmail().toEditable()
        }

        nameButton.setOnClickListener {
            val newName = nameText.text
            handler.setNewName(newName.toString())
            Toast.makeText(requireContext(), "Name Changed", Toast.LENGTH_LONG).show()
        }

        emailButton.setOnClickListener {
            val newEmail = emailText.text
            handler.setNewEmail(newEmail.toString())
            Toast.makeText(requireContext(), "Username Changed", Toast.LENGTH_LONG).show()
        }

        val buttonAddress = root.findViewById<Button>(R.id.editAddress)
        val buttonPassword = root.findViewById<Button>(R.id.editPassword)
        buttonAddress.setOnClickListener { showCustomDialog() }
        buttonPassword.setOnClickListener { showCustomDialog2() }
        
        handler = DatabaseHelper(requireContext())

        return root
    }
}