package com.old.leopards.restaurant.ui

import android.content.Context
import android.widget.Toast

class Global {
    companion object {
        var userId: Int = 0

        fun showText(context: Context?, text: String) {
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
