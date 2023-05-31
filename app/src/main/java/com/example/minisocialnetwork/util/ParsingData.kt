package com.example.minisocialnetwork.util

import com.example.minisocialnetwork.util.Constants.AT_SIGN
import com.example.minisocialnetwork.util.Constants.EMPTY_STR

/**
 * Object for parsing data related to user information.
 */
object ParsingData {

    /**
     * Extracts the username from an email address.
     * @param email The email address from which to extract the username.
     * @return The extracted username with proper formatting.
     */
    fun getUserName(email: String): String {
        val userName = StringBuilder()
        var counter = 0
        while (email[counter] != AT_SIGN) {
            if (!email[counter].isLetter()) {
                userName.append(EMPTY_STR)
            } else {
                userName.append(email[counter])
            }
            counter++
        }
        return userName.toString().split(EMPTY_STR).joinToString(EMPTY_STR)
            .replaceFirstChar { it.uppercase() }.trim()
    }

}