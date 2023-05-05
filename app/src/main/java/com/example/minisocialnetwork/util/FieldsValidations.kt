package com.example.minisocialnetwork.util

import android.util.Patterns
import java.util.regex.Pattern

object FieldsValidations {

    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isStringContainNumber(password: String): Boolean {
        return Pattern.compile(".*[0-9].*").matcher(password).matches()
    }

    fun isMixedCase(password: String): Boolean {
        val lowerCasePatternMatcher = Pattern.compile(".*[a-z].*").matcher(password)
        val upperCasePatternMatcher = Pattern.compile(".*[A-Z].*").matcher(password)
        return if (!lowerCasePatternMatcher.matches()) {
            false
        } else upperCasePatternMatcher.matches()
    }


}