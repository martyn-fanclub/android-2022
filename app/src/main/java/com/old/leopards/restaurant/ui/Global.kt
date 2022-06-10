package com.old.leopards.restaurant.ui

import android.content.Context
import android.widget.Toast
import com.old.leopards.restaurant.database.entities.User

class Global {
    companion object {
        val emailPattern = "([a-zA-Z0-9]+\\.*[a-zA-Z0-9]+)+@([a-zA-Z0-9]+\\.*[a-zA-Z0-9]+)+".toRegex()
        val REQUEST_CODE = 100

        fun showText(context: Context?, text: String) {
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
