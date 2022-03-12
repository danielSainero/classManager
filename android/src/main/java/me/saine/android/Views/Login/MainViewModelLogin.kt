package com.example.classmanegerandroid.Views.Login

import android.content.IntentSender.OnFinished
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import me.saine.android.Classes.CurrentUser
import me.saine.android.dataClasses.appUser


class MainViewModelLogin: ViewModel() {
    var lista: MutableList<Int> = mutableListOf()
    var auth: FirebaseAuth
    var db: FirebaseFirestore

    init {
        db = FirebaseFirestore.getInstance()
        auth = Firebase.auth
        for(i in 0..100) {
            lista.add(i)
        }
    }

    fun saveCurrentUser(onFinished: () -> Unit) {
        db.collection("users").get().addOnSuccessListener {
            for (document in it) {
                if(document.id.equals(auth.currentUser?.uid.toString())) {
                    val currentUser = appUser(
                        name = document.get("name") as String,
                        email = document.get("email") as String,
                        imgPath = document.get("imgPath") as String,
                        courses = document.get("courses") as MutableList<String>,
                        classes = document.get("classes") as MutableList<String>
                    )
                    CurrentUser.currentUser = currentUser
                    CurrentUser.getMyCourses()
                    CurrentUser.getMyClasses()
                    onFinished()
                }
            }
        }
    }
}