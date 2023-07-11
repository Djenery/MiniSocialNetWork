package com.example.minisocialnetwork.data

import com.example.minisocialnetwork.data.repository.DataStoreInterface
import com.example.minisocialnetwork.domain.repository.LocalAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreInterface
): LocalAuthRepository {
    override suspend fun saveSignUpCredentials(singUpModel: SingUpModel) {
        dataStore.saveSignUpCredentials(singUpModel)
    }

    override suspend fun getSignUpCredentials(): SingUpModel =
        withContext(Dispatchers.IO) {
            return@withContext dataStore.getSignUpCredentials()
        }

}