package com.example.minisocialnetwork.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.base.BaseFragment
import com.example.minisocialnetwork.databinding.FragmentAuthBinding
import com.example.minisocialnetwork.datastore.StoreUserData
import com.example.minisocialnetwork.util.Constants
import com.example.minisocialnetwork.util.FieldsValidations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        autoLogin()
        setListeners()


        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    /**
     * Checks if dataStore contains data if does then redirect user to another activity
     */
    private fun autoLogin() {
        lifecycleScope.launch(Dispatchers.Main) {
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
                lifecycleScope.launch(Dispatchers.IO) {
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
            binding.singUpEMail.error = Constants.ERROR_EMPTY_STRING
            return false
        } else if (!FieldsValidations.isValidEmail(binding.singUpEMailEt.text.toString())) {
            binding.singUpEMail.error = Constants.ERROR_INVALID_EMAIL
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
            binding.singUpPassword.error = Constants.ERROR_EMPTY_STRING
            return false
        } else if (binding.singUpPasswordT.text.toString().length < 8) {
            binding.singUpPassword.error = Constants.ERROR_TOO_SHORT_PASSWORD
            return false
        } else if (!FieldsValidations.isStringContainNumber(binding.singUpPasswordT.text.toString())) {
            binding.singUpPassword.error = Constants.ERROR_AT_LEAST_ONE_DIGIT
            return false
        } else if (!FieldsValidations.isMixedCase(binding.singUpPasswordT.text.toString())) {
            binding.singUpPassword.error = Constants.ERROR_UPPER_AND_LOWER
            return false
        } else {
            binding.singUpPassword.isErrorEnabled = false
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
    override fun setListeners() {
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

    override fun setObservers() {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance() = AuthFragment()
    }
}
