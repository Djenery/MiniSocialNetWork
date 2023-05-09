package com.example.minisocialnetwork.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivitySingUpBinding
import com.example.minisocialnetwork.util.FieldsValidations.isValidEmail
import com.example.minisocialnetwork.util.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.util.FieldsValidations.isMixedCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch




class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding

    private val Context.dataStore by preferencesDataStore(name = "user_login_info")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLoginData()
        setUpListeners()
        autoLogin()
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
                lifecycleScope.launch {
                    dataStore.edit {
                        it[PreferencesKeys.EMAIL_KEY] = singUpEMailEt.text.toString()
                        it[PreferencesKeys.PASSWORD_KEY] = singUpPasswordT.text.toString()
                        it[PreferencesKeys.CHECK_BOS_KEY] = singUpCheckbox.isChecked
                    }
                }
            }
        }
    }


    private fun autoLogin() {
        if (binding.singUpCheckbox.isChecked) {
            passDataToAnotherActivity()
        }
    }

    private fun getLoginData() {
            dataStore.data.map {
                binding.singUpEMailEt.setText(it[PreferencesKeys.EMAIL_KEY] ?: "")
                binding.singUpPasswordT.setText(it[PreferencesKeys.PASSWORD_KEY] ?: "")
                binding.singUpCheckbox.isChecked = it[PreferencesKeys.CHECK_BOS_KEY] ?: false
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

    private object PreferencesKeys {
        val EMAIL_KEY = stringPreferencesKey("email")
        val PASSWORD_KEY = stringPreferencesKey("password")
        val CHECK_BOS_KEY = booleanPreferencesKey("checkBox")
    }


    inner class FieldsValidation(private val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (view.id == R.id.sing_up_e_mail) {
                validateEmail()
            } else {
                validatePassword()
            }
        }
    }

}
