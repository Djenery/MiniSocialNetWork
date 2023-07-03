package com.example.minisocialnetwork.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minisocialnetwork.data.AuthRepositoryImpl
import com.example.minisocialnetwork.data.SingUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    private val liveData = MutableLiveData<SingUpModel>()
    val result: LiveData<SingUpModel> = liveData


    init {
        viewModelScope.launch {
            liveData.value = authRepositoryImpl.getCredentials()
        }
    }

}