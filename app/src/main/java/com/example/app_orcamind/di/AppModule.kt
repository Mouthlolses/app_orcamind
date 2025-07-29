package com.example.app_orcamind.di

import com.example.app_orcamind.data.datasource.FirebaseAuthDataSource
import com.example.app_orcamind.data.repository.UserRegistration
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(auth: FirebaseAuth): FirebaseAuthDataSource {
        return FirebaseAuthDataSource(auth)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: FirebaseAuthDataSource): UserRegistration {
        return UserRegistration(dataSource)
    }
}