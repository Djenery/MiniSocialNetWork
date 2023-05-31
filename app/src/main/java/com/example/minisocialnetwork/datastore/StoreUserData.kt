package com.example.minisocialnetwork.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.minisocialnetwork.util.Constants.DATA_STORE_NAME
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.Constants.PASSWORD
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Class responsible for storing and retrieving user data using DataStore.
 * @param context The application context.
 */
class StoreUserData(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            DATA_STORE_NAME
        )
        val EMAIL_KEY = stringPreferencesKey(EMAIL)
        val PASSWORD_KEY = stringPreferencesKey(PASSWORD)
    }

    /**
     * Retrieves the saved email from DataStore.
     * @return The saved email, or an empty string if not available.
     */
    suspend fun getEmail(): String {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY].orEmpty()
        }.first()
    }

    /**
     * Retrieves the saved password from DataStore.
     * @return The saved password, or an empty string if not available.
     */
    suspend fun getPassword(): String {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY].orEmpty()
        }.first()
    }


    /**
     * Saves the login credentials to DataStore.
     * @param email The email to save.
     * @param password The password to save.
     */
    suspend fun saveLoginToDataStore(email: String, password: String) {
        context.dataStore.edit {
            it[EMAIL_KEY] = email
            it[PASSWORD_KEY] = password
        }
    }

}