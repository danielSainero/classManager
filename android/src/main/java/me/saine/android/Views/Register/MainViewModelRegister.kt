package me.saine.android.Views.Register

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import me.saine.android.data.network.AccesToDataBase
import java.util.regex.Pattern

class MainViewModelRegister: ViewModel() {

     fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        navController: NavController
    ) {
        AccesToDataBase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")

                    Toast.makeText(context,"Has sido registrado correctamente", Toast.LENGTH_LONG).show()
                    setInformationUser(
                        email = email,
                        navController = navController
                    )
                } else {
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context,"El usuario no ha podido ser creado", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setInformationUser(
        email: String,
        navController: NavController
    ) {

        AccesToDataBase.db.collection("users")
            .document(AccesToDataBase.auth.currentUser?.uid.toString())
            .set(
                hashMapOf(
                    "courses" to  mutableListOf<String>(),
                    "classes" to mutableListOf<String>(),
                    "name" to "userName",
                    "email" to email,
                    "imgPath" to "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2FdefaultUserImg.png?alt=media&token=eb869349-7d2b-4b9a-b04a-b304c0366c78",
                    "id" to AccesToDataBase.auth.currentUser?.uid.toString(),
                    "description" to "myDescription"
                )
            )
            .addOnSuccessListener {
                navController.popBackStack()
            }
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