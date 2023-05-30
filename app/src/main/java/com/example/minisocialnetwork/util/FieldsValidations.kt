package com.example.minisocialnetwork.util

import android.util.Patterns
import com.example.minisocialnetwork.util.Constants.DIGITS
import com.example.minisocialnetwork.util.Constants.LOWERCASE_LETTERS
import com.example.minisocialnetwork.util.Constants.UPPERCASE_LETTERS
import java.util.regex.Pattern

object FieldsValidations {

    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isStringContainNumber(password: String): Boolean {
        return Pattern.compile(DIGITS).matcher(password).matches()
    }

    fun isMixedCase(password: String): Boolean {
        val lowerCasePatternMatcher = Pattern.compile(LOWERCASE_LETTERS).matcher(password)
        val upperCasePatternMatcher = Pattern.compile(UPPERCASE_LETTERS).matcher(password)
        return if (!lowerCasePatternMatcher.matches()) {
            false
        } else upperCasePatternMatcher.matches()
    }


}