package com.example.minisocialnetwork.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivitySingUpBinding
import com.example.minisocialnetwork.datastore.StoreUserData
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.FieldsValidations.isMixedCase
import com.example.minisocialnetwork.util.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.util.FieldsValidations.isValidEmail
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
        setUpListeners()
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
    private fun setUpListeners() {
        with(binding) {
            singUpEMailEt.doOnTextChanged { _, _, _, _ ->
                validateEmail()
            }
            singUpPasswordT.doOnTextChanged { _, _, _, _ ->
                validatePassword()
            }
            singUpRegisterBt.setOnClickListener {
                if (validateEmail() && validatePassword()) {
                    saveData()
                    passDataToAnotherActivity()
                }
            }
        }
    }

    /**
     * Saves email and password inputs in dataStore.
     */
    private fun saveData() {
        with(binding) {
            if (singUpCheckbox.isChecked) {
                lifecycleScope.launch(IO) {
                    storeUserData.saveLoginToDataStore(
                        singUpEMailEt.text.toString(),
                        singUpPasswordT.text.toString()
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
            binding.singUpEMail.error = getString(R.string.error_empty_string)
            return false
        } else if (!isValidEmail(binding.singUpEMailEt.text.toString())) {
            binding.singUpEMail.error = getString(R.string.error_invalid_email)
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
            binding.singUpPassword.error = getString(R.string.error_empty_string)
            return false
        } else if (binding.singUpPasswordT.text.toString().length < 8) {
            binding.singUpPassword.error = getString(R.string.error_too_short_passowrd)
            return false
        } else if (!isStringContainNumber(binding.singUpPasswordT.text.toString())) {
            binding.singUpPassword.error = getString(R.string.error_atleast_one_digit)
            return false
        } else if (!isMixedCase(binding.singUpPasswordT.text.toString())) {
            binding.singUpPassword.error = getString(R.string.error_upper_and_lower_case)
            return false
        } else {
            binding.singUpPassword.isErrorEnabled = false
        }
        return true
    }


}
