package com.example.classmanegerandroid.Views

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.classmanegerandroid.Navigation.Destinations
import me.saine.android.Views.Register.MainViewModelRegister

@Composable
fun MainRegister(
    navController: NavController,
    mainViewModelRegister: MainViewModelRegister
) {

    var context = LocalContext.current
    var emailText = remember{ mutableStateOf("test@gmail.com") }
    var passwordText = remember{ mutableStateOf("11111111") }
    var repeatPasswordText = remember{ mutableStateOf("11111111") }
    val (checkedStatePrivacyPolicies,onValueChangecheckedStatePrivacyPolicies) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Registrarse")
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
                            OutlinedTextField(
                                value = repeatPasswordText.value,
                                onValueChange = {
                                    repeatPasswordText.value = it
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
                            Row (
                                content = {
                                    /*Checkbox(
                                        checked = checkedStatePrivacyPolicies.value,
                                        onCheckedChange = { checkedStatePrivacyPolicies.value = it },
                                    )*/
                                    labelledCheckbox(
                                        labelText = "Politicas de Privacidad",
                                        isCheckedValue = checkedStatePrivacyPolicies,
                                        onValueChangeCheked = onValueChangecheckedStatePrivacyPolicies,
                                        onClickText = {navController.navigate(Destinations.PrivacyPolicies.route)}
                                    )
                                }
                            )

                        }
                        item {
                            Button(
                                content = {
                                    Text(text = "Registrarse")
                                },
                                onClick = {
                                    if (repeatPasswordText.value.equals(passwordText.value)) {
                                            createUser(
                                                email = emailText.value,
                                                password = passwordText.value,
                                                mainViewModelRegister = mainViewModelRegister,
                                                context = context,
                                                navController = navController
                                            )
                                    }
                                    else {
                                        Toast.makeText(context,"Las claves deben ser iguales",Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(start = 40.dp, end = 40.dp))
                            )
                        }
                    }
                )
            }
        },
    )
}

private fun createUser(
    email: String,
    password: String,
    mainViewModelRegister: MainViewModelRegister,
    context: Context,
    navController: NavController
) {
    mainViewModelRegister.auth.
    createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                Log.d(ContentValues.TAG, "createUserWithEmail:success")
                val user = mainViewModelRegister.auth.currentUser

                Toast.makeText(context,"Has sido registrado correctamente",Toast.LENGTH_LONG).show()
                setInformationUser(
                    mainViewModelRegister = mainViewModelRegister,
                    email = email,
                    password = password
                )
                reload(
                    navController = navController
                )
            } else {
                Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(context,"El usuario no ha podido ser creado",Toast.LENGTH_LONG).show()
            }
        }
}

private fun reload(
    navController: NavController
) {
    navController.popBackStack()
}

private fun setInformationUser(
    mainViewModelRegister: MainViewModelRegister,
    email: String,
    password: String
) {
    mainViewModelRegister.db.collection("users").document(mainViewModelRegister.auth.currentUser?.uid.toString())
        .set(
            hashMapOf(
                "courses" to  mutableListOf<String>("LKfzK0WThqPnjTt4H63RQ8DXikI3"),
                "classes" to mutableListOf<String>("7doxqoJ7a110l8ljXZpV"),
                "name" to "userName",
                "email" to email,
                "imgPath" to "uri",
            )
        )
}

@Composable
private fun labelledCheckbox(
    labelText: String,
    isCheckedValue: Boolean,
    onValueChangeCheked: (Boolean) -> Unit,
    onClickText: () -> Unit
) {
    Row(
        modifier = Modifier.padding(start = 30.dp,8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Checkbox(
            checked = isCheckedValue,
            onCheckedChange = { onValueChangeCheked(it) },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Blue)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "${labelText}",
            modifier = Modifier
                .clickable{
                    onClickText()
                },
            fontSize = 15.sp,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.padding(25.dp))
    }
}