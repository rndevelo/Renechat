package com.rndeveloper.renechat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rndeveloper.renechat.repositories.ChatRepository
import com.rndeveloper.renechat.repositories.ChatRepositoryImpl
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
    fun provideLoginRepository(fireStore: FirebaseFirestore): ChatRepository =
        ChatRepositoryImpl(fireStore = fireStore)
}
