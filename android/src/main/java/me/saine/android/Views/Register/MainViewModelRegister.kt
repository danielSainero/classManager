package me.saine.android.Views.Register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainViewModelRegister: ViewModel() {
    var auth: FirebaseAuth
    var db: FirebaseFirestore
    var database: DatabaseReference

    init {
        db = FirebaseFirestore.getInstance()
        auth = Firebase.auth
        database = Firebase.database.reference

    }
}