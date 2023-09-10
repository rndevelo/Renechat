package com.rndeveloper.renechat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rndeveloper.renechat.repositories.ChatRepository
import com.rndeveloper.renechat.repositories.ChatRepositoryImpl
import com.rndeveloper.renechat.repositories.LoginRepository
import com.rndeveloper.renechat.repositories.LoginRepositoryImpl
import com.rndeveloper.renechat.repositories.UsersListRepository
import com.rndeveloper.renechat.repositories.UsersListRepositoryImpl
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
    fun provideLoginRepository(firebaseAuth: FirebaseAuth, fireStore: FirebaseFirestore): LoginRepository =
        LoginRepositoryImpl(firebaseAuth = firebaseAuth, fireStore = fireStore)

    @Singleton
    @Provides
    fun provideChatRepository(firebaseAuth: FirebaseAuth, fireStore: FirebaseFirestore): ChatRepository =
        ChatRepositoryImpl(firebaseAuth = firebaseAuth, fireStore = fireStore)
    @Singleton
    @Provides
    fun provideUsersListRepository(fireStore: FirebaseFirestore): UsersListRepository =
        UsersListRepositoryImpl(fireStore = fireStore)

}
