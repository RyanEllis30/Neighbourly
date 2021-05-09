package com.example.neighbourly.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.neighbourly.*

const val usernameKey = "com.example.neighbourly.displayName"

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    lateinit var handler:DatabaseHelper

    fun showLoginMenu(view: View) {
        setContentView(R.layout.activity_login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        handler = DatabaseHelper(this)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val forceEntryButton = findViewById<Button>(R.id.forceEntryButton)
        val register = findViewById<Button>(R.id.register)

        forceEntryButton.setOnClickListener {//This is the force entry button, it will be deleted upon launch, bypasses database login
            loading.visibility = View.VISIBLE
            loginViewModel.login("hello", "password")
        }

        register.setOnClickListener{//This is the register button, it will attempt to register a user based on the data in the textboxes
            // Collect email and password from main page
            val emailText = username.text.toString()
            val passwordText = password.text.toString()

            // Use DB Handler to search for user then store result
            handler.findUserExists("$emailText", "$passwordText")
            val value = handler.fetchResult()

            if (value <= 0) { // Value = amount of records with the same email
                handler.insertUserData("$emailText", "$passwordText")
                showLoginFailed("Account created")
            }
            else { // if value > 1 that means account exists
                showLoginFailed("That email already exists")
            }
        }

        login.setOnClickListener { // This is the login button, it will attempt to login a user based on the data in the textboxes
            // Collect email and password from main page
            val emailText = username.text.toString()
            val passwordText = password.text.toString()

            // Use DB Handler to search for user then store result
            handler.findUser("$emailText", "$passwordText")
            val value = handler.fetchResult()

            if (value <= 0) { // Value = amount of records with the same email and password
                showLoginFailed("Incorrect email or password")
            }
            else { // Successful login, log in user
                loading.visibility = View.VISIBLE
                loginViewModel.login(emailText, passwordText)
            }
        }

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login/register button unless both username / password is valid
            login.isEnabled = loginState.isDataValid
            register.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed("Incorrect email or password")
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        val intent = Intent(this, Navigation::class.java).apply {
            putExtra(displayName, usernameKey)
        }
        startActivity(intent)
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                "$welcome $displayName",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}