package com.example.neighbourly.data

import com.example.neighbourly.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String): Result<LoggedInUser> {
        return try {
            // TODO: handle loggedInUser authentication
            val user = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
            Result.Success(user)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}