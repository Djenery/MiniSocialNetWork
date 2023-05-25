package com.example.minisocialnetwork.util.extentions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText


fun Activity.hideKeyboardOnOutsideTouch(event: MotionEvent) {
    if (event.action == MotionEvent.ACTION_DOWN) {
        currentFocus?.let { focusedView ->
            if (focusedView is TextInputEditText && isTouchOutSideView(event, focusedView)) {
                focusedView.clearFocus()
                hideKeyboard()
            }
        }
    }
}

private fun isTouchOutSideView(event: MotionEvent, focusedView: TextInputEditText): Boolean {
    val outRect = Rect()
    focusedView.getGlobalVisibleRect(outRect)
    return !outRect.contains(event.rawX.toInt(), event.rawY.toInt())

}

fun Activity.hideKeyboard() {
    val inputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let { focusedView ->
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}
