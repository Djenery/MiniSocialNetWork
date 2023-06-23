package com.example.minisocialnetwork.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentAuthBinding
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.presentation.viewmodels.AuthViewModel
import com.example.minisocialnetwork.util.FieldsValidations.isMixedCase
import com.example.minisocialnetwork.util.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.util.FieldsValidations.isValidEmail
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    private val mViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
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
                if (validateEmail() && validatePassword()) {
                    saveData()
                    moveToMyProfile()
                }

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
     * Passes data to another activity
     */
    private fun moveToMyProfile() {
        if (NAV_GRAPH) {
            val action = SingUpFragmentDirections.actionSingUpFragmentToMyProfileFragment()
            findNavController().navigate(action)
        } else {
            parentFragmentManager.commit {
                replace(R.id.authFragmentContainer, MyContactsFragment.newInstance())
            }
        }
    }
    //        val intent = Intent(this, MyProfileActivity::class.java)
//        intent.putExtra(Constants.EMAIL, binding.singUpEMailEt.text.toString())
//        startActivity(intent)
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//        finish()


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

    companion object {
        fun newInstance() = SingUpFragment()
    }
}
