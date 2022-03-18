package me.saine.android.Views.Register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class MainViewModelRegister: ViewModel() {
    var auth: FirebaseAuth
    var db: FirebaseFirestore
    var database: DatabaseReference

    init {
        db = FirebaseFirestore.getInstance()
        auth = Firebase.auth
        database = Firebase.database.reference

    }

    //Validaciones
    fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$", Pattern.CASE_INSENSITIVE).matcher(text).find()
    fun isValidPassword(text: String) = Pattern.compile("^[a-zA-Z0-9_]{8,}\$",Pattern.CASE_INSENSITIVE).matcher(text).find()

    fun checkAllValidations(
        textEmail: String,
        textPassword: String,
        checkedStatePrivacyPolicies: Boolean
    ): Boolean {
        if (
            !isValidEmail(text = textEmail) ||
            !isValidPassword(text = textPassword) ||
            !checkedStatePrivacyPolicies
        )  return false
        return  true
    }
}