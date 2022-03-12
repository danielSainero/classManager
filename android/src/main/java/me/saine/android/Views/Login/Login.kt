package com.example.classmanegerandroid.Views.Login

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations

@Composable
fun MainLogin(
    navController: NavController,
    mainViewModelLogin: MainViewModelLogin
) {
    var context = LocalContext.current
    var emailText = remember{ mutableStateOf("test@gmail.com") }
    var passwordText = remember{ mutableStateOf("11111111") }


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
                                    data = "https://www.psicoactiva.com/wp-content/uploads/puzzleclopedia/Libros-codificados-300x262.jpg"
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
    /*
    val user = auth.currentUser
    if(user != null){
        reload();
    }*/
}

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