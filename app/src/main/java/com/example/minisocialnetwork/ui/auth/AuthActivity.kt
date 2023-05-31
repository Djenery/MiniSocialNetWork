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
import com.example.minisocialnetwork.util.Constants.ERROR_AT_LEAST_ONE_DIGIT
import com.example.minisocialnetwork.util.Constants.ERROR_EMPTY_STRING
import com.example.minisocialnetwork.util.Constants.ERROR_INVALID_EMAIL
import com.example.minisocialnetwork.util.Constants.ERROR_TOO_SHORT_PASSWORD
import com.example.minisocialnetwork.util.Constants.ERROR_UPPER_AND_LOWER
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
        val intent = Intent(this, MyProfileActivity::class.java)
        intent.putExtra(EMAIL, binding.singUpEMailEt.text.toString())
        startActivity(intent)
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
    private fun validateEmail(): Boolean {
        if (binding.singUpEMailEt.text.toString().trim().isEmpty()) {
            binding.singUpEMail.error = ERROR_EMPTY_STRING
            return false
        } else if (!isValidEmail(binding.singUpEMailEt.text.toString())) {
            binding.singUpEMail.error = ERROR_INVALID_EMAIL
            return false
        } else {
            binding.singUpEMail.isErrorEnabled = false
        }

        return true
    }

    /**
     * Checks if password field is valid
     * @return true if password is valid to all checks, false otherwise
     */
    private fun validatePassword(): Boolean {
        if (binding.singUpPasswordT.text.toString().trim().isEmpty()) {
            binding.singUpPassword.error = ERROR_EMPTY_STRING
            return false
        } else if (binding.singUpPasswordT.text.toString().length < 8) {
            binding.singUpPassword.error = ERROR_TOO_SHORT_PASSWORD
            return false
        } else if (!isStringContainNumber(binding.singUpPasswordT.text.toString())) {
            binding.singUpPassword.error = ERROR_AT_LEAST_ONE_DIGIT
            return false
        } else if (!isMixedCase(binding.singUpPasswordT.text.toString())) {
            binding.singUpPassword.error = ERROR_UPPER_AND_LOWER
            return false
        } else {
            binding.singUpPassword.isErrorEnabled = false
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


}
