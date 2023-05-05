package com.example.minisocialnetwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.minisocialnetwork.databinding.ActivitySingUpBinding
import com.example.minisocialnetwork.FieldsValidations.isValidEmail
import com.example.minisocialnetwork.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.FieldsValidations.isMixedCase
import com.example.minisocialnetwork.databinding.ActivityMyProfileBinding


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListeners()
        binding.singUpRegisterBt.setOnClickListener {
            if (isValidate()) {
                startActivity(
                    Intent(this, MyProfileActivity::class.java)
                        .putExtra("email", binding.singUpEMailEt.text.toString())
                )
            }
        }

    }

    private fun setUpListeners() {
        binding.singUpEMailEt.addTextChangedListener(FieldsValidation(binding.singUpEMail))
        binding.singUpPasswordT.addTextChangedListener(FieldsValidation(binding.singUpPassword))
    }

    private fun isValidate() = validateEmail() && validatePassword()

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


    inner class FieldsValidation(private val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.sing_up_e_mail -> {
                    validateEmail()
                }

                R.id.sing_up_password -> {
                    validatePassword()
                }
            }
        }
    }


}

