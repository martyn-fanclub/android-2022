package com.old.leopards.restaurant.ui

import android.content.Context
import android.widget.Toast
import com.old.leopards.restaurant.database.entities.User

class Global {
    companion object {
        var currentUser: User = User(0, "", "", "", "")

        fun showText(context: Context?, text: String) {
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
