package com.old.leopards.restaurant.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.entities.User

class Global {
    companion object {
        var currentUser: User = User(0, "", "", "", "")
        val emailPattern = "([a-zA-Z0-9]+\\.*[a-zA-Z0-9]+)+@([a-zA-Z0-9]+\\.*[a-zA-Z0-9]+)+".toRegex()
        val REQUEST_CODE = 100
        var userAddress = ""

        fun showText(context: Context?, text: String) {
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
