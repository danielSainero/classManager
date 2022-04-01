package com.example.classmanegerandroid.Views.Login

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import com.example.classmanegerandroid.Navigation.navController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import me.saine.android.Classes.CurrentUser
import me.saine.android.data.remote.appUser
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
                        id = document.id,
                        name = document.get("name") as String,
                        email = document.get("email") as String,
                        imgPath = document.get("imgPath") as String,
                        courses = document.get("courses") as MutableList<String>,
                        classes = document.get("classes") as MutableList<String>,
                        description = document.get("description") as String
                    )
                    CurrentUser.currentUser = currentUser
                    CurrentUser.updateDates()
                    onFinished()
                }
            }
        }
    }

    fun finishLogin(accountTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = accountTask.getResult(ApiException::class.java)
                account?.idToken?.let { token ->
                    val credential = GoogleAuthProvider.getCredential(token, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                checkIfIsNewAccount(account)
                            }
                            else {
                                Log.w(ContentValues.TAG, "Google sign in failed")
                            }

                        }
                }
        } catch (e: ApiException) {
            Log.w(ContentValues.TAG, "Google sign in failed", e)
        }
    }

    private fun checkIfIsNewAccount(
        account: GoogleSignInAccount
    ) {
        db.collection("users")
            .document(auth.currentUser?.uid.toString())
            .get()
            .addOnCompleteListener() {
                if (it.result.exists())
                    saveCurrentUser() { navController.navigate(Destinations.MainAppView.route) }
                else
                    setInformationUser(account)
            }
    }

    private fun setInformationUser(
        account: GoogleSignInAccount
    ) {
        db.collection("users").document(auth.currentUser?.uid.toString())
            .set(
                hashMapOf(
                    "courses" to  mutableListOf<String>(),
                    "classes" to mutableListOf<String>(),
                    "name" to account.displayName,
                    "email" to account.email,
                    "imgPath" to "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2FdefaultUserImg.png?alt=media&token=eb869349-7d2b-4b9a-b04a-b304c0366c78",
                    "id" to auth.currentUser?.uid.toString(),
                    "description" to "myDescription"
                )
            )
        saveCurrentUser() {
            navController.navigate(Destinations.MainAppView.route)
        }
    }

    fun signIn(
        email: String,
        password: String,
        mainViewModelLogin: MainViewModelLogin,
        context: Context,
        navController: NavController
    ) {
        mainViewModelLogin.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d("Inicio de sesión", "Se ha iniciado la sesión")
                    Toast.makeText(context,"Usted se ha logeado correctamente", Toast.LENGTH_SHORT).show()
                    mainViewModelLogin.saveCurrentUser() {
                        navController.navigate(Destinations.MainAppView.route)
                    }

                } else {
                    Log.w("Inicio de sesión", "No se ha podido iniciar la sesión", task.exception)
                    Toast.makeText(context, "El usuario o contraseña no son válidos.", Toast.LENGTH_LONG).show()
                }
            }
    }

    //Validaciones
    fun isValidPassword(text: String) = Pattern.compile("^[a-zA-Z0-9_]{8,}\$", Pattern.CASE_INSENSITIVE).matcher(text).find()


}