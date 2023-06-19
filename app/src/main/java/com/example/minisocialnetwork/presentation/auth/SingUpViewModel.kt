package com.example.minisocialnetwork.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minisocialnetwork.data.AuthRepositoryImpl
import com.example.minisocialnetwork.data.SingUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    private val liveData = MutableLiveData<SingUpModel>()
    val result: MutableLiveData<SingUpModel> = liveData

    fun saveCredentials(email: String, password: String) {
        viewModelScope.launch(IO) {
            authRepositoryImpl.saveCredentials(SingUpModel(email, password))
        }
    }

    fun getCredentials() {
        viewModelScope.launch(IO) {
            liveData.value = authRepositoryImpl.getCredentials()
        }
    }

}