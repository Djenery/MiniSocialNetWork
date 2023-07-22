package com.example.minisocialnetwork.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivitySingUpBinding
import com.example.minisocialnetwork.datastore.StoreUserData
import com.example.minisocialnetwork.ui.myProfile.MyProfileActivity
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.FieldsValidations.isMixedCase
import com.example.minisocialnetwork.util.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.util.FieldsValidations.isValidEmail
import com.example.minisocialnetwork.util.extentions.hideKeyboardOnOutsideTouch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

/**
 * Activity class for user authentication.
 */
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    private lateinit var storeUserData: StoreUserData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storeUserData = StoreUserData(this)
        autoLogin()
        setListeners()
    }

    /**
     * Checks if dataStore contains data if does then redirect user to another activity
     */
    private fun autoLogin() {
        lifecycleScope.launch(Main) {
            val email = storeUserData.getEmail()
            val password = storeUserData.getPassword()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.singUpEMailEt.setText(email)
                passDataToAnotherActivity()
            }
        }
    }

    /**
     * Passes data to another activity
     */
    private fun passDataToAnotherActivity() {
        startActivity(
            Intent(this, MyProfileActivity::class.java).apply {
                putExtra(EMAIL, binding.singUpEMailEt.text.toString())
            }
        )
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    /**
     * Set listeners which user can interact with in current activity
     */
    private fun setListeners() {
        with(binding) {
            singUpEMailEt.doOnTextChanged { _, _, _, _ ->
                validateEmail()
            }
            singUpPasswordT.doOnTextChanged { _, _, _, _ ->
                validatePassword()
            }
            singUpRegisterBt.setOnClickListener {
                if (isValidate()) {
                    saveData()
                    passDataToAnotherActivity()
                }

            }
        }
    }

    /**
     * Checks if email and password fields are valid.
     * @return true if fields are valid to all checks otherwise false
     */
    private fun isValidate() = validateEmail() && validatePassword()

    /**
     * Saves email and password inputs in dataStore
     */
    private fun saveData() {
        with(binding) {
            if (singUpCheckbox.isChecked) {
                lifecycleScope.launch(IO) {
                    storeUserData.saveLoginToDataStore(
                        singUpEMailEt.text.toString(), singUpPasswordT.text.toString()
                    )
                }
            }
        }
    }

    /**
     * Checks if email field is valid
     * @return true if password is valid to all checks, false otherwise
     */
    private fun validateEmail() = with(binding) {
        if (singUpEMailEt.text.toString().trim().isEmpty()) {
            singUpEMail.error = getString(R.string.error_empty_string)
            false
        } else if (!isValidEmail(singUpEMailEt.text.toString())) {
            singUpEMail.error = R.string.error_invalid_email.toString()
            false
        } else {
            singUpEMail.isErrorEnabled = false
            true
        }
    }


    /**
     * Checks if password field is valid
     * @return true if password is valid to all checks, false otherwise
     */
    private fun validatePassword(): Boolean {
        with(binding) {
            if (singUpPasswordT.text.toString().trim().isEmpty()) {
                singUpPassword.error = R.string.error_empty_string.toString()
                return false
            } else if (singUpPasswordT.text.toString().length < MIN_PASSWORD_LENGTH) {
                singUpPassword.error =
                    getString(R.string.error_too_short_passowrd, MIN_PASSWORD_LENGTH)
                return false
            } else if (!isStringContainNumber(singUpPasswordT.text.toString())) {
                singUpPassword.error = R.string.error_atleast_one_digit.toString()
                return false
            } else if (!isMixedCase(singUpPasswordT.text.toString())) {
                singUpPassword.error = R.string.error_upper_and_lower_case.toString()
                return false
            } else {
                singUpPassword.isErrorEnabled = false
            }
        }
        return true
    }

    /**
     * Overrides the activity's dispatchTouchEvent method to handle touch events.
     * This method is used to hide the keyboard when a touch is made outside of TextInputEditText.
     * @param event The touch screen event.
     * @return true if this event was consumed; otherwise, false.
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        hideKeyboardOnOutsideTouch(event)
        return super.dispatchTouchEvent(event)
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }
}
