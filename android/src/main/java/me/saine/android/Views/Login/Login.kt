package com.example.classmanegerandroid.Views.Login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.compose.rememberImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.example.classmanegerandroid.Navigation.Destinations
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import me.saine.android.Classes.CurrentUser.Companion.auth
import me.saine.android.R.string.default_web_client_id

fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

@Composable
fun MainLogin(
    navController: NavController,
    mainViewModelLogin: MainViewModelLogin
) {
    val context = LocalContext.current
    val activity = context as Activity
    val loginWithGoogle = remember { mutableStateOf(false) }
    val emailText = remember{ mutableStateOf("test@gmail.com") }
    val passwordText = remember{ mutableStateOf("11111111") }
    if (loginWithGoogle.value){
        signInWithGoogle(
            activity = activity
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Login")
                },
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    content = {
                        item {
                            Image(
                                painter = rememberImagePainter(
                                    data = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Flogo.png?alt=media&token=a9d234bf-a791-44cd-ad29-fd18b54f488e"
                                ),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .height(250.dp)
                                    .width(450.dp)
                                    .padding(30.dp)
                                    .clickable {
                                        navController.navigate(Destinations.MainAppView.route)
                                    }
                            )
                        }
                        item {
                            OutlinedTextField(
                                value = emailText.value,
                                onValueChange = {
                                    emailText.value = it
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.LightGray
                                ),
                                placeholder = { Text("Email") },
                                singleLine = true,
                                label = { Text(text = "Email") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(start = 40.dp, end = 40.dp))
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = passwordText.value,
                                onValueChange = {
                                    passwordText.value = it
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.LightGray
                                ),
                                placeholder = { Text("Password") },
                                singleLine = true,
                                label = { Text(text = "Password") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(start = 40.dp, end = 40.dp))
                            )
                        }
                        item {
                            Button(
                                content = {
                                    Text(text = "Iniciar sesión")
                                },
                                onClick = {

                                    signIn(
                                        email = emailText.value,
                                        password =passwordText.value,
                                        mainViewModelLogin = mainViewModelLogin,
                                        context = context,
                                        navController = navController
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(start = 40.dp, end = 40.dp))
                            )
                        }
                        item {
                            Text(
                                text = "O bien",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(start = 40.dp, end = 40.dp))
                            )
                        }
                        item {
                            Button(
                                content = {
                                    Text(text = "Iniciar sesión con Google")
                                },
                                onClick = {
                                    //Comprobar inicio de sesión con google
                                    loginWithGoogle.value = true

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(start = 40.dp, end = 40.dp))
                            )
                        }
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(start = 40.dp, end = 40.dp))
                            ) {
                                Text(
                                    text = "He olvidado la contraseña",
                                    color = Color.Blue
                                )
                                Text(
                                    text = "Crear usuario",
                                    color = Color.Blue,
                                    modifier = Modifier
                                        .clickable {
                                            navController.navigate(Destinations.Register.route)
                                        }
                                )
                            }
                        }
                    }
                )
            }
        },
    )
}


private fun signIn(
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
                Toast.makeText(context,"Usted se ha logeado correctamente",Toast.LENGTH_SHORT).show()
                mainViewModelLogin.saveCurrentUser() {
                    reload(
                        navController = navController
                    )
                }

            } else {
                Log.w("Inicio de sesión", "No se ha podido iniciar la sesión", task.exception)
                Toast.makeText(context, "El usuario o contraseña no son válidos.", Toast.LENGTH_LONG).show()
            }
        }
}

@Override
fun onStart() {
    onStart()

    val user = auth.currentUser
    if(user != null){
       // reload();
    }
    // Check if user is signed in (non-null) and update UI accordingly.
    val currentUser = auth.currentUser
    //updateUI(currentUser)
}

@Composable
fun signInWithGoogle(
    activity: Activity
){
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(stringResource(default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    val signInIntent = googleSignInClient.signInIntent
    activity.startActivityForResult(signInIntent, 1)

}





private fun firebaseAuthWithGoogle(
    idToken: String,
    navController: NavController,
    context: Context
) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information

                val user = auth.currentUser
                Toast.makeText(context,"Ha ido",Toast.LENGTH_SHORT).show()
                navController.navigate(Destinations.MainAppView.route)
                //updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(context,"No Ha ido",Toast.LENGTH_SHORT).show()

                Log.w(TAG, "signInWithCredential:failure", task.exception)
               // updateUI(null)
            }
        }
}
/*
private fun signInGoogle(googleSignInClient :GoogleSignInClient) {
    val signInIntent = googleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
}
*/
/*
private fun setInformationUser() {
    db.collection("users").document(auth.currentUser?.uid.toString())
        .set(
            hashMapOf(
                //"password" to binding.registerPassword.text.toString(),
                "name" to "",
                "email" to auth.currentUser?.email.toString(),
                "imgPath" to "gs://appvote-bdc78.appspot.com/imgUser/sainorum.png",
                "ofertas" to "true"
            )
        )
}*/

private fun reload(
    navController: NavController
) {
    navController.navigate(Destinations.MainAppView.route)
}

/*
private fun allUsers() {
    var tmp: Boolean = false

    db.collection("users").get().addOnSuccessListener {

        for (document in it) {

            if(document.id.equals(auth.currentUser?.uid))
                tmp = true
        }
        if(!tmp)
            setInformationUser()

    }
}*/