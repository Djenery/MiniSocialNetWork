package com.example.minisocialnetwork.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.coroutineScope
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivitySingUpBinding
import com.example.minisocialnetwork.datastore.StoreUserData
import com.example.minisocialnetwork.util.FieldsValidations.isValidEmail
import com.example.minisocialnetwork.util.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.util.FieldsValidations.isMixedCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding

    private lateinit var storeUserData: StoreUserData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storeUserData = StoreUserData(this)
        getLoginData()
        setUpListeners()
        binding.singUpRegisterBt.setOnClickListener {
            clickButton()
        }
    }

    private fun clickButton() {
        if (isValidate()) {
            saveDataIfChecked()
            passDataToAnotherActivity()
        }
    }

    private fun saveDataIfChecked() {
        with(binding) {
            if (singUpCheckbox.isChecked) {
                CoroutineScope(IO).launch {
                    storeUserData.saveData(
                        singUpEMailEt.text.toString(),
                        singUpPasswordT.text.toString(),
                        singUpCheckbox.isChecked
                    )
                }
            }
        }
    }

    private fun autoLogin(isAutoLogin: Boolean) {
        if (isAutoLogin) {
            passDataToAnotherActivity()
        }
    }

    private fun getLoginData() {
        lifecycle.coroutineScope.launch {
            storeUserData.getEmail().collect {
                binding.singUpEMailEt.setText(it)
            }
        }
        lifecycle.coroutineScope.launch {
            storeUserData.getPassword().collect {
                binding.singUpPasswordT.setText(it)
            }
        }
        lifecycle.coroutineScope.launch {
            storeUserData.getCheckBoxState().collect {
                autoLogin(it)
            }
        }
    }

    private fun setUpListeners() {
        binding.singUpEMailEt.addTextChangedListener(FieldsValidation(binding.singUpEMail))
        binding.singUpPasswordT.addTextChangedListener(FieldsValidation(binding.singUpPassword))
    }

    private fun validateEmail(): Boolean {
        if (binding.singUpEMailEt.text.toString().trim().isEmpty()) {
            binding.singUpEMail.error = "Required field!"
            binding.singUpEMail.requestFocus()
            return false
        } else if (!isValidEmail(binding.singUpEMailEt.text.toString())) {
            binding.singUpEMail.error = "Invalid email!"
            binding.singUpEMail.requestFocus()
            return false
        } else {
            binding.singUpEMail.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        if (binding.singUpPasswordT.text.toString().trim().isEmpty()) {
            binding.singUpPassword.error = "Required Field!"
            binding.singUpPassword.requestFocus()
            return false
        } else if (binding.singUpPasswordT.text.toString().length < 8) {
            binding.singUpPassword.error = "password can't be less than 8"
            binding.singUpPassword.requestFocus()
            return false
        } else if (!isStringContainNumber(binding.singUpPasswordT.text.toString())) {
            binding.singUpPassword.error = "Required at least 1 digit"
            binding.singUpPassword.requestFocus()
            return false
        } else if (!isMixedCase(binding.singUpPasswordT.text.toString())) {
            binding.singUpPassword.error =
                "Password must contain upper and lower case letters"
            binding.singUpPassword.requestFocus()
            return false
        } else {
            binding.singUpPassword.isErrorEnabled = false
        }

        return true
    }

    private fun passDataToAnotherActivity() {
        val intent = Intent(this, MyProfileActivity::class.java)
        intent.putExtra("email", binding.singUpEMailEt.text.toString())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun isValidate() = validateEmail() && validatePassword()

    inner class FieldsValidation(private val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (view == binding.singUpEMail) {
                validateEmail()
            } else {
                validatePassword()
            }
        }
    }

}
