package com.example.minisocialnetwork.util

import com.example.minisocialnetwork.util.Constants.AT_SIGN
import com.example.minisocialnetwork.util.Constants.EMPTY_STR
import java.lang.StringBuilder

object ParsingData {

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