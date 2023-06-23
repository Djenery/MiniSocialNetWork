package com.example.minisocialnetwork.data

import com.example.minisocialnetwork.domain.repository.LocalAuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val localAuthRepository: LocalAuthRepository
) {
    suspend fun saveCredentials(singUpModel: SingUpModel) {
        localAuthRepository.saveSignUpCredentials(singUpModel)
    }

    suspend fun getCredentials(): SingUpModel {
        return localAuthRepository.getSignUpCredentials()
    }

}