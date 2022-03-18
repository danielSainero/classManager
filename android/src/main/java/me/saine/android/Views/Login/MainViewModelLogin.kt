package com.example.classmanegerandroid.Views.Login

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import me.saine.android.Classes.CurrentUser
import me.saine.android.R
import me.saine.android.dataClasses.ListItem
import me.saine.android.dataClasses.appUser
import java.util.regex.Pattern


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
                    CurrentUser.updateDates()
                    onFinished()
                }
            }
        }
    }

    //Validaciones
    fun isValidPassword(text: String) = Pattern.compile("^[a-zA-Z0-9_]{8,}\$", Pattern.CASE_INSENSITIVE).matcher(text).find()


}