package com.rndeveloper.renechat.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.rndeveloper.renechat.model.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : ChatRepository {

    override fun getMessages(): Flow<Result<List<Message>>> =
        callbackFlow {
            fireStore.collection("messages")
                .addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        val items = snapshot.toObjects(Message::class.java)
                        items.forEach { item ->
                            Log.d("MESSAGE", "getMessagesRepo: $item")
                        }
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

    override fun setMessage(message: Message): Flow<Result<Void>> =
        callbackFlow {
            val tag = fireStore.collection("messages").document().id
            fireStore.collection("messages").document(tag).set(message)
                .addOnSuccessListener {
                    trySend(Result.success(it))
                    close()
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                    close()
                }
            awaitClose()
        }
}

interface ChatRepository {
    fun getMessages(): Flow<Result<List<Message>>>
    fun setMessage(message: Message): Flow<Result<Void>>
}