package com.example.minisocialnetwork.presentation.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentAuthBinding
import com.example.minisocialnetwork.presentation.activities.MyContactsActivity
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.presentation.viewmodels.AuthViewModel
import com.example.minisocialnetwork.util.FieldsValidations.isMixedCase
import com.example.minisocialnetwork.util.FieldsValidations.isStringContainNumber
import com.example.minisocialnetwork.util.FieldsValidations.isValidEmail
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

/**

 * A sign-up fragment that allows the user to register with an email and password.
 * This fragment extends the BaseFragment class and
 * provides functionality for validating the email and password fields,
 * saving the entered data in dataStore, and
 * navigating to the MyProfileFragment upon successful registration.
 * It also handles hiding the keyboard when the user interacts with the fragment.
 * @see BaseFragment
 * @see AuthViewModel
 */
@AndroidEntryPoint
class SingUpFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val mViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    /**
     *Sets up listeners for user interaction in the current fragment.
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
                    navigateToMyProfile()
                }
            }
            binding.root.setOnClickListener {
                hideKeyboard()
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
                singUpEMail.error = getString(R.string.error_empty_string)
                return false
            } else if (!isValidEmail(singUpEMailEt.text.toString())) {
                singUpEMail.error = getString(R.string.error_invalid_email)
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
                singUpPassword.error = getString(R.string.error_empty_string)
                return false
            } else if (singUpPasswordT.text.toString().length < 8) {
                singUpPassword.error = getString(R.string.error_too_short_password)
                return false
            } else if (!isStringContainNumber(singUpPasswordT.text.toString())) {
                singUpPassword.error = getString(R.string.error_at_least_one_digit)
                return false
            } else if (!isMixedCase(singUpPasswordT.text.toString())) {
                singUpPassword.error = getString(R.string.error_upper_and_lower_case)
                return false
            } else {
                singUpPassword.isErrorEnabled = false
            }
        }
        return true
    }

    /**
     * Saves email and password inputs in dataStore.
     */
    private fun saveData() {
        with(binding) {
            if (singUpCheckbox.isChecked) {
                mViewModel.saveCredentials(
                    singUpEMailEt.text.toString(), singUpPasswordT.text.toString()
                )
            }
        }
    }

    /**

     * Navigates to the MyProfileFragment.
     * The navigation is performed either using the Navigation component or the FragmentManager,
     * depending on the value of NAV_GRAPH.
     */
    private fun navigateToMyProfile() {
        if (NAV_GRAPH) {
            val action = SingUpFragmentDirections.actionSingUpFragmentToMyContactsActivity()
            findNavController().navigate(action)
        } else {
            val intent = Intent(context, MyContactsActivity::class.java)
            requireActivity().finish()
            startActivity(intent)
            requireActivity().overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }
    }

    /**

     * Hides the keyboard.
     */
    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    companion object {
        /**
         * Creates a new instance of the SingUpFragment.
         *
         * @return A new instance of SingUpFragment.
         */
        fun newInstance() = SingUpFragment()
    }
}
