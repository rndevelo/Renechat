package com.rndeveloper.renechat.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rndeveloper.renechat.model.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : ChatRepository {

//    private val documentReference =
//        fireStore.collection("users")
//            .document(firebaseAuth.uid.toString())
//            .collection("chats")
//            .document("${firebaseAuth.uid-}")

    override fun getMessages(myUid: String, otherUid: String): Flow<Result<List<Message>>> =
        callbackFlow {
            val list = listOf(myUid, otherUid).sorted()
            val documentReference =
                fireStore.collection("chats")
                    .document("${list.first()}x${list.last()}")
                    .collection("chat")

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

    override fun setMessage(myUid: String, otherUid: String, message: Message): Flow<Result<Void>> =
        callbackFlow {
            val list = listOf(myUid, otherUid).sorted()
            val documentReference =
                fireStore.collection("chats")
                    .document("${list.first()}x${list.last()}")
                    .collection("chat")

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
    fun getMessages(myUid: String, otherUid: String): Flow<Result<List<Message>>>
    fun setMessage(myUid: String, otherUid: String, message: Message): Flow<Result<Void>>
}