package com.example.minisocialnetwork.data

import com.example.minisocialnetwork.domain.repository.LocalAuthRepository

class AuthRepositoryImpl(
    private val localAuthRepository: LocalAuthRepository
) {
    suspend fun saveCredentials(singUpModel: SingUpModel) {
        localAuthRepository.saveSignUpCredentials(singUpModel)
    }

    suspend fun getCredentials(): SingUpModel {
        return localAuthRepository.getSignUpCredentials()
    }

}