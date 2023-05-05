package com.example.minisocialnetwork.util

import java.lang.StringBuilder

class ParsingData {

    fun getUserName(email: String): String {
        val regex = Regex("[a-zA-Z]")
        val array = email.toCharArray()
        val userName = StringBuilder()
        var counter = 0
        while (array[counter] != '@') {
            if (!array[counter].toString().matches(regex)) {
                userName.append(" ")
            } else {
                userName.append(array[counter])
            }
            counter++
        }
        return userName.toString().trim()
    }

}