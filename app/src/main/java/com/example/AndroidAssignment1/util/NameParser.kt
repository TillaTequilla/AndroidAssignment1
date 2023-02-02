package com.example.AndroidAssignment1.util

// if we have util class that works with String, why not move regexp validation here
// can be object if only contains static methods
class NameParser {
    companion object {
        fun getName(email: String): String {
            val indexOfPoint = email.indexOf('.')
            val indexOfAtSign = email.indexOf('@')
            val name = email.substring(0, indexOfPoint).lowercase().apply {
                replaceFirstChar { it.uppercaseChar() } // no need to reassign the value, same for line 13
            }
            val surname = email.substring(indexOfPoint + 1, indexOfAtSign).replaceFirstChar { it.uppercaseChar() }

            return "$name $surname"
        }
    }
}