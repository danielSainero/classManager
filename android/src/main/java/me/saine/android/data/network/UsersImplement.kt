package me.saine.android.data.network

import android.content.IntentSender.OnFinished
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import me.saine.android.Classes.CurrentUser
import me.saine.android.data.remote.appUser

class UsersImplement {
    companion object {
        var auth: FirebaseAuth = Firebase.auth
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var storage = Firebase.storage
        var storageReference: StorageReference = storage.reference

        fun updateUser(
            idOfUser: String,
            user: appUser,
            onFinished: (Boolean) -> Unit
        ) {
            db.collection("users")
                .document(idOfUser)
                .set(user)
                .addOnSuccessListener {
                    onFinished(true)
                }
        }
    }
}