package me.saine.android.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import me.saine.android.Classes.CurrentUser
import me.saine.android.data.network.UsersImplement.Companion.updateUser
import me.saine.android.data.remote.Course
import me.saine.android.data.remote.appUser

class CourseImplement {
    companion object {
        var auth: FirebaseAuth = Firebase.auth
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var storage = Firebase.storage
        var storageReference: StorageReference = storage.reference

        fun createNewCourse(
            newCourse: Course,
            onFinished: (Boolean, Course) -> Unit
        ) {
            val document = db.collection("course").document()
            newCourse.id = document.id
            document.set(newCourse)
                .addOnSuccessListener {
                    onFinished(true, newCourse)
            }
        }

        fun updateCourse(
            newCourse: Course,
            onFinished: (Boolean, Course) -> Unit
        ) {
            AccesToDataBase.db.collection("course")
                .document(newCourse.id)
                .set(newCourse)
                .addOnSuccessListener {
                    onFinished(true, newCourse)
                }
        }

    }
}