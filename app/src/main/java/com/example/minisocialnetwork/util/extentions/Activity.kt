package com.example.minisocialnetwork.util.extentions

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText

/**
 * Hides keyboard if touch was outside of TextInputEditText
 * @param event – The touch screen event.
 */
fun Activity.hideKeyboardOnOutsideTouch(event: MotionEvent) {
    if (event.action == MotionEvent.ACTION_DOWN) {
        currentFocus?.let { focusedView ->
            if (focusedView is TextInputEditText) {
                focusedView.clearFocus()
                hideKeyboard()
            }
        }
    }
}

/**
 * Calls only if touch event was made outside of TextInputEditText view and then hide the keyboard
 */
fun Activity.hideKeyboard() {
    val inputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let { focusedView ->
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}
