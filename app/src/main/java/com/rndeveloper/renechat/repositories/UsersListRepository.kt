package com.rndeveloper.renechat.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.rndeveloper.renechat.model.UserData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UsersListRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore
) : UsersListRepository {

    private val documentReference = fireStore.collection("users")

    override fun getUsers(): Flow<Result<List<UserData>>> =
        callbackFlow {
            documentReference.addSnapshotListener { snapshot, e ->
                val items = snapshot?.toObjects(UserData::class.java)
                if (items != null) {
                    trySend(Result.success(items))
//                        close()
                } else {
                    if (e != null) {
                        trySend(Result.failure(e.fillInStackTrace()))
                    }
//                        close()
                }
            }
            awaitClose()
        }
}

interface UsersListRepository {
    fun getUsers(): Flow<Result<List<UserData>>>
}