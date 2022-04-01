package me.saine.android.data.network

import android.content.IntentSender.OnFinished
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class PracticeImplement {
    companion object {
        var auth: FirebaseAuth = Firebase.auth
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var storage = Firebase.storage
        var storageReference: StorageReference = storage.reference


        fun deletePracticeById(
            idOfPractice: String,
            onFinished: (Boolean) -> Unit
        ) {
            db.collection("practices")
                .document(idOfPractice)
                .delete()
                .addOnSuccessListener {
                    onFinished(true)
                }
        }
    }
}