package me.saine.android.Views.Login.ForgotPassword

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.saine.android.data.network.AccesToDataBase.Companion.auth


@Composable
fun MainForgotPassword(
    navController: NavController,
    mainViewModelForgotPassword: MainViewModelForgotPassword
) {
    var (emailText,onValueChangeEmailText) = remember{ mutableStateOf("") }
    var (emailError,emailErrorChange) = remember { mutableStateOf(false) }
    val nameOfEmailError = remember { mutableStateOf("El email no es válido: ejemplo@ejemplo.eje") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(text = "Recuperar tu cuenta")
                 }
             )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .border(BorderStroke(1.dp, Color.LightGray)),
                content = {
                    Text(
                        text = "Introduce tu correo electrónico para buscar tu cuenta",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(
                                PaddingValues(
                                    start = 40.dp,
                                    end = 40.dp,
                                    top = 10.dp,
                                    bottom = 3.dp
                                )
                            )
                    )
                    bigTextFieldWithErrorMesaje(
                        text = "Email",
                        value = emailText,
                        onValueChange = onValueChangeEmailText,
                        validateError = mainViewModelForgotPassword::isValidEmail,
                        errorMesaje = nameOfEmailError.value,
                        changeError = emailErrorChange,
                        error = emailError,
                        mandatory = true,
                        KeyboardType = KeyboardType.Text
                    )
                    Button(
                        content = {
                            Text(text = "Enviar correo de verificación")
                        },
                        onClick = {
                            auth.sendPasswordResetEmail(emailText)
                                .addOnCompleteListener { task ->
                                    Toast.makeText(context, "Le hemos enviado un correo", Toast.LENGTH_SHORT).show()

                                    if (task.isSuccessful)
                                        Toast.makeText(context, "La contraseña ha sido cambiada", Toast.LENGTH_SHORT).show()
                                }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(start = 40.dp, end = 40.dp))
                    )
                }
            )
        }
    )
        
    
}

