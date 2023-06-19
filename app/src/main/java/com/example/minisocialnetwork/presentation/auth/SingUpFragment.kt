package com.example.minisocialnetwork.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentAuthBinding
import com.example.minisocialnetwork.presentation.base.BaseFragment
import com.example.minisocialnetwork.util.FieldsValidations.isMixedCase
import com.example.minisocialnetwork.util.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.util.FieldsValidations.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val mViewModel: SingUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        autoLogin()
        setListeners()

        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

//    /**
//     * Checks if dataStore contains data if does then redirect user to another activity
//     */
//    private fun autoLogin() {
//        lifecycleScope.launch(Dispatchers.Main) {
//            val email = storeUserData.getEmail()
//            val password = storeUserData.getPassword()
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                binding.singUpEMailEt.setText(email)
//                passDataToAnotherActivity()
//            }
//        }
//    }


    /**
     * Passes data to another activity
     */
    private fun passDataToAnotherActivity() {
//        val intent = Intent(this, MyProfileActivity::class.java)
//        intent.putExtra(Constants.EMAIL, binding.singUpEMailEt.text.toString())
//        startActivity(intent)
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//        finish()
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
                mViewModel.saveCredentials(
                    singUpEMailEt.text.toString(),
                    singUpPasswordT.text.toString()
                )
            }
        }
    }

    /**
     * Checks if email field is valid
     * @return true if password is valid to all checks, false otherwise
     */
    private fun validateEmail(): Boolean {
        with(binding) {
            if (singUpEMailEt.text.toString().trim().isEmpty()) {
                singUpEMail.error = R.string.error_empty_string.toString()
                return false
            } else if (!isValidEmail(singUpEMailEt.text.toString())) {
                singUpEMail.error = R.string.error_invalid_email.toString()
                return false
            } else {
                singUpEMail.isErrorEnabled = false
            }
        }
        return true
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
            } else if (singUpPasswordT.text.toString().length < 8) {
                singUpPassword.error = R.string.error_too_short_passowrd.toString()
                return false
            } else if (!isStringContainNumber(singUpPasswordT.text.toString())) {
                singUpPassword.error = R.string.error_at_least_one_digit.toString()
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

//    /**
//     * Overrides the activity's dispatchTouchEvent method to handle touch events.
//     * This method is used to hide the keyboard when a touch is made outside of TextInputEditText.
//     * @param event The touch screen event.
//     * @return true if this event was consumed; otherwise, false.
//     */
//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        hideKeyboardOnOutsideTouch(event)
//        return super.dispatchTouchEvent(event)
//    }


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

    companion object {
        fun newInstance() = SingUpFragment()
    }
}
