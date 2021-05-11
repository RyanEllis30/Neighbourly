package com.example.neighbourly.ui.settings

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.neighbourly.Navigation
import com.example.neighbourly.R
import com.example.neighbourly.ui.login.LoginActivity

class SettingsFragment: Fragment() {

    lateinit var handler: LoginActivity

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        handler = LoginActivity()
        val logOutButton = root.findViewById<Button>(R.id.btnLogout)

        logOutButton.setOnClickListener { // This is the logout button

            val intent = Intent(activity, LoginActivity::class.java).apply {}
            startActivity(intent)
        }

        return root
    }
}