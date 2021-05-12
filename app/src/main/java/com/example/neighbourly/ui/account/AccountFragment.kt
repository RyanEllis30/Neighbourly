package com.example.neighbourly.ui.account

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
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
            }
            else{
                println("Task failed")
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
        val buttonAddress = root.findViewById<Button>(R.id.editAddress)
        val buttonPassword = root.findViewById<Button>(R.id.editPassword)
        buttonAddress.setOnClickListener { showCustomDialog() }
        buttonPassword.setOnClickListener { showCustomDialog2() }

        //buttonAddress.setOnClickListener {
        // Handler code here.
        //   val intent = Intent(context, addressPopup::class.java)
        //   startActivity(intent);
        //}
        handler = DatabaseHelper(requireContext())

        return root
    }
}