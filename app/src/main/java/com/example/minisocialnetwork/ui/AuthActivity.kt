package com.example.minisocialnetwork.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivitySingUpBinding
import com.example.minisocialnetwork.datastore.StoreUserData
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.Constants.ERROR_AT_LEAST_ONE_DIGIT
import com.example.minisocialnetwork.util.Constants.ERROR_EMPTY_STRING
import com.example.minisocialnetwork.util.Constants.ERROR_INVALID_EMAIL
import com.example.minisocialnetwork.util.Constants.ERROR_TOO_SHORT_PASSWORD
import com.example.minisocialnetwork.util.Constants.ERROR_UPPER_AND_LOWER
import com.example.minisocialnetwork.util.FieldsValidations.isValidEmail
import com.example.minisocialnetwork.util.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.util.FieldsValidations.isMixedCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


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

    private fun clickButton() {
        if (isValidate()) {
            saveData()
            passDataToAnotherActivity()
        }
    }

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

    private fun setUpListeners() {
        with(binding) {
            singUpEMailEt.doOnTextChanged { _, _, _, _ ->
                validateEmail()
            }
            singUpPasswordT.doOnTextChanged { _, _, _, _ ->
                validatePassword()
            }
            singUpRegisterBt.setOnClickListener {
                clickButton()
            }
        }
    }

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

    private fun passDataToAnotherActivity() {
        val intent = Intent(this, MyProfileActivity::class.java)
        intent.putExtra(EMAIL, binding.singUpEMailEt.text.toString())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun isValidate() = validateEmail() && validatePassword()


}
