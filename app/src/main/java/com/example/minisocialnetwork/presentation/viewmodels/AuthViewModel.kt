package com.example.minisocialnetwork.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minisocialnetwork.data.AuthRepositoryImpl
import com.example.minisocialnetwork.data.SingUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for handling authentication-related data and actions.
 * This ViewModel is annotated with @HiltViewModel, indicating it is eligible for dependency injection
 * using Hilt. It depends on the AuthRepositoryImpl for authentication operations.
 * @property authRepositoryImpl The repository implementation responsible for authentication operations.
 * @property liveData MutableLiveData holding the SignUpModel data.
 * @property result LiveData providing access to the SignUpModel data.
 * @property _isAutoLoginEnabled MutableLiveData indicating whether auto login is enabled.
 * @property isAutoLoginEnabled LiveData providing access to the auto login enabled state.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    private val liveData = MutableLiveData<SingUpModel>()
    private val result: LiveData<SingUpModel> = liveData

    private val _isAutoLoginEnabled = MutableLiveData<Boolean>()
    val isAutoLoginEnabled: LiveData<Boolean> = _isAutoLoginEnabled

    init {
        // Initialize the ViewModel and observe the authentication state
        viewModelScope.launch {
            while (isActive) {
                val data = authRepositoryImpl.getCredentials()
                _isAutoLoginEnabled.value = data.email.isNotEmpty() && data.password.isNotEmpty()
                break

            }
        }
    }

    /**

     * Saves the user's credentials and updates the auto login state.
     * @param email The email to be saved.
     * @param password The password to be saved.
     */
    fun saveCredentials(email: String, password: String) {
        val data = SingUpModel(email, password)
        updateAutoLoginState(data)

        viewModelScope.launch(IO) {
            authRepositoryImpl.saveCredentials(data)
        }
    }

    /**

     * Updates the auto login state based on the provided SignUpModel data.
     * This method sets the value of liveData to the provided data,
     * allowing observers to access the updated data.
     * It also sets the value of _isAutoLoginEnabled to true, indicating that auto login is enabled.
     * @param data The SignUpModel data to update the auto login state with.
     */
    private fun updateAutoLoginState(data: SingUpModel) {
        liveData.value = data
        _isAutoLoginEnabled.value = true
    }
}