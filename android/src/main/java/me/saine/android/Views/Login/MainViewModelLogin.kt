package com.example.classmanegerandroid.Views.Login

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainViewModelLogin: ViewModel() {
    var lista: MutableList<Int> = mutableListOf()
    /*var auth: FirebaseAuth
    var db: FirebaseFirestore*/

    init {
       /* db = FirebaseFirestore.getInstance()
        auth = Firebase.auth*/
        for(i in 0..100) {
            lista.add(i)
        }
    }
}