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

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    private val liveData = MutableLiveData<SingUpModel>()
    val result: LiveData<SingUpModel> = liveData

    private var _isAutoLoginEnabled = MutableLiveData<Boolean?>(null)
    val isAutoLoginEnabled: LiveData<Boolean?> = _isAutoLoginEnabled

    init {
        viewModelScope.launch() {
            while (isActive) {
                val data = authRepositoryImpl.getCredentials()
                _isAutoLoginEnabled.value = data.email.isNotEmpty() && data.password.isNotEmpty()
                break

            }
        }
    }

    fun saveCredentials(email: String, password: String) {
        viewModelScope.launch(IO) {
            authRepositoryImpl.saveCredentials(SingUpModel(email, password))
        }
    }


}