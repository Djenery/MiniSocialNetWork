package com.example.minisocialnetwork.data.repository

import androidx.datastore.preferences.core.edit
import com.example.minisocialnetwork.data.SingUpModel
import com.example.minisocialnetwork.data.repository.StoreUserData.Companion.dataStore
import kotlinx.coroutines.flow.first

interface DataStoreInterface {

    suspend fun getSignUpCredentials(): SingUpModel
    /**
     * Saves the login credentials to DataStore.
     * @param singUpModel The loginModel contains login credentials to save in dataStore
     */
    suspend fun saveSignUpCredentials(singUpModel: SingUpModel)
}