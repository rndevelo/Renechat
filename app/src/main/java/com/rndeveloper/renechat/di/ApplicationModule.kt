package com.rndeveloper.renechat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rndeveloper.renechat.repositories.ChatRepository
import com.rndeveloper.renechat.repositories.ChatRepositoryImpl
import com.rndeveloper.renechat.repositories.LoginRepository
import com.rndeveloper.renechat.repositories.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository =
        LoginRepositoryImpl(firebaseAuth = firebaseAuth)

    @Singleton
    @Provides
    fun provideChatRepository(fireStore: FirebaseFirestore): ChatRepository =
        ChatRepositoryImpl(fireStore = fireStore)
}
