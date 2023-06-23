package com.example.minisocialnetwork.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.minisocialnetwork.domain.repository.LocalAuthRepository
import com.example.minisocialnetwork.util.Constants.DATA_STORE_NAME
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.Constants.PASSWORD
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class responsible for storing and retrieving user data using DataStore.
 * @param appContext The application context.
 */
@Singleton
class StoreUserData @Inject constructor(private val appContext: Context) :
    LocalAuthRepository {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            DATA_STORE_NAME
        )
        val EMAIL_KEY = stringPreferencesKey(EMAIL)
        val PASSWORD_KEY = stringPreferencesKey(PASSWORD)
    }

    /**
     * Retrieves the saved login credentials from DataStore.
     * @return loginModel which contains login credentials
     */
    override suspend fun getSignUpCredentials(): SingUpModel {
        return appContext.dataStore.data.first().let { preferences ->
            val email = preferences[EMAIL_KEY].orEmpty()
            val password = preferences[PASSWORD_KEY].orEmpty()
            SingUpModel(email, password)
        }
    }

    /**
     * Saves the login credentials to DataStore.
     * @param singUpModel The loginModel contains login credentials to save in dataStore
     */
    override suspend fun saveSignUpCredentials(singUpModel: SingUpModel) {
        appContext.dataStore.edit {
            it[EMAIL_KEY] = singUpModel.email
            it[PASSWORD_KEY] = singUpModel.password
        }
    }

}