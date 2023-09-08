package com.rndeveloper.renechat.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.rndeveloper.renechat.model.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore
) : ChatRepository {

    private val documentReference = fireStore.collection("chat").document("messages").collection("puta")

    override fun getMessages(): Flow<Result<List<Message>>> =
        callbackFlow {
            documentReference.addSnapshotListener { snapshot, e ->
                    val items = snapshot?.toObjects(Message::class.java)
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

    override fun setMessage(message: Message): Flow<Result<Void>> =
        callbackFlow {
            val tag = documentReference.document().id
            documentReference.document(tag).set(message)
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