package com.example.minisocialnetwork.di

import android.content.Context
import com.example.minisocialnetwork.data.repository.DataStoreInterface
import com.example.minisocialnetwork.data.repository.StoreUserData
import com.example.minisocialnetwork.domain.repository.LocalAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStoreInterface {
        return StoreUserData(context)
    }




}