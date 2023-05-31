package com.example.minisocialnetwork.util

import android.util.Patterns
import com.example.minisocialnetwork.util.Constants.DIGITS
import com.example.minisocialnetwork.util.Constants.LOWERCASE_LETTERS
import com.example.minisocialnetwork.util.Constants.UPPERCASE_LETTERS
import java.util.regex.Pattern

/**
 * Object containing various field validation methods.
 */
object FieldsValidations {

    /**
     * Checks if the provided email address is valid.
     * @param email The email address to be validated.
     * @return `true` if the email address is valid, `false` otherwise.
     */
    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    /**
     * Checks if the provided password contains at least one digit.
     * @param password The password to be checked.
     * @return `true` if the password contains at least one digit, `false` otherwise.
     */
    fun isStringContainNumber(password: String): Boolean {
        return Pattern.compile(DIGITS).matcher(password).matches()
    }

    /**
     * Checks if the provided password contains both lowercase and uppercase letters.
     * @param password The password to be checked.
     * @return `true` if the password contains both lowercase and uppercase letters, `false` otherwise.
     */
    fun isMixedCase(password: String): Boolean {
        val lowerCasePatternMatcher = Pattern.compile(LOWERCASE_LETTERS).matcher(password)
        val upperCasePatternMatcher = Pattern.compile(UPPERCASE_LETTERS).matcher(password)
        return if (!lowerCasePatternMatcher.matches()) {
            false
        } else upperCasePatternMatcher.matches()
    }


}