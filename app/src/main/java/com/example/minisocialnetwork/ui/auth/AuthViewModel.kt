package com.example.minisocialnetwork.ui.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.minisocialnetwork.datastore.StoreUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(application: Application) : ViewModel() {

    private var storeUserData: StoreUserData = StoreUserData(application)
}