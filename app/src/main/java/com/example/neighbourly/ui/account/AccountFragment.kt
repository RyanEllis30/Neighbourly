package com.example.neighbourly.ui.account

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.neighbourly.R

class AccountFragment : Fragment() {

    private lateinit var changeAddressDialog: AlertDialog
    fun showCustomDialog() {
        val inflater: LayoutInflater = this.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.activity_address_popup, null)

        //val header_txt = dialogView.findViewById<TextView>(R.id.ChangeAddress)
        //header_txt.text = "Header Message"
        //val details_txt = dialogView.findViewById<TextView>(R.id.housenumber)
        val cancel: Button = dialogView.findViewById(R.id.CancelAddress)
        cancel.setOnClickListener {
            changeAddressDialog.cancel()
        }
        val update: Button = dialogView.findViewById(R.id.updateAddress)
        update.setOnClickListener {
            // add code to update the address
        }
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(arg0: DialogInterface) {

            }
        })
        dialogBuilder.setView(dialogView)

        changeAddressDialog = dialogBuilder.create();
        //alertDialog.window!!.getAttributes().windowAnimations = R.style.PauseDialogAnimation
        changeAddressDialog.show()
    }

    private lateinit var changePasswordDialog: AlertDialog
    fun showCustomDialog2() {
        val inflater2: LayoutInflater = this.getLayoutInflater()
        val dialogView2: View = inflater2.inflate(R.layout.activity_password_popup, null)

        //val header_txt = dialogView.findViewById<TextView>(R.id.ChangeAddress)
        //header_txt.text = "Header Message"
        //val details_txt = dialogView.findViewById<TextView>(R.id.housenumber)
        val cancel2: Button = dialogView2.findViewById(R.id.CancelPassword)
        cancel2.setOnClickListener {
            changePasswordDialog.cancel()
        }
        val update2: Button = dialogView2.findViewById(R.id.updatePassword)
        update2.setOnClickListener {
            // add code to update the address
        }
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(arg0: DialogInterface) {

            }
        })
        dialogBuilder.setView(dialogView2)

        changePasswordDialog = dialogBuilder.create();
        //alertDialog.window!!.getAttributes().windowAnimations = R.style.PauseDialogAnimation
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

        return root
    }
}