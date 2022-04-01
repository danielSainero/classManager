package me.saine.android.Views.Register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.classmanegerandroid.Navigation.Destinations

@Composable
fun MainRegister(
    navController: NavController,
    mainViewModelRegister: MainViewModelRegister
) {
    //Texts
    var (emailText,onValueChangeEmailText) = remember{ mutableStateOf("test@gmail.com") }
    var (emailError,emailErrorChange) = remember { mutableStateOf(false) }
    val nameOfEmailError = remember { mutableStateOf("El email no es válido: ejemplo@ejemplo.eje") }

    var (passwordText,onValueChangePasswordText) = remember{ mutableStateOf("11111111") }
    var (passwordError,passwordErrorChange) = remember { mutableStateOf(false) }
    val passwordTextErrorMesaje = remember { mutableStateOf("La contraseña no puede ser inferior a 8 caracteres ni contener caracteres especiales") }

    var (repeatPasswordText,onValueChangeRepeatPasswordText) = remember{ mutableStateOf("11111111") }
    var (repeatPasswordError,repeatPasswordErrorChange) = remember { mutableStateOf(false) }
    val repeatPasswordTextErrorMesaje = remember { mutableStateOf("La contraseña no puede ser inferior a 8 caracteres ni contener caracteres especiales") }


    //Help variables
    var context = LocalContext.current
    val (checkedStatePrivacyPolicies,onValueChangecheckedStatePrivacyPolicies) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Crear cuenta")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Go back",
                                tint = Color.White,
                            )
                        }
                    )
                }
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
                            bigTextFieldWithErrorMesaje(
                                text = "Email",
                                value = emailText,
                                onValueChange = onValueChangeEmailText,
                                validateError =  mainViewModelRegister::isValidEmail,
                                errorMesaje = nameOfEmailError.value,
                                changeError = emailErrorChange,
                                error = emailError,
                                mandatory = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }

                        item {
                           bigPasswordInputWithErrorMesaje(
                               value = passwordText,
                               onValueChangeValue = onValueChangePasswordText,
                               valueError = passwordError,
                               onValueChangeError = passwordErrorChange,
                               errorMesaje = passwordTextErrorMesaje.value,
                               validateError = mainViewModelRegister::isValidPassword,
                               mandatory = true,
                               keyboardType = KeyboardType.Text
                           )
                        }
                        item {
                            bigPasswordInputWithErrorMesaje(
                                value = repeatPasswordText,
                                onValueChangeValue = onValueChangeRepeatPasswordText,
                                valueError = repeatPasswordError,
                                onValueChangeError = repeatPasswordErrorChange,
                                errorMesaje = repeatPasswordTextErrorMesaje.value,
                                validateError = mainViewModelRegister::isValidPassword,
                                mandatory = true,
                                keyboardType = KeyboardType.Text
                            )
                        }

                        item {
                            Row (
                                content = {
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
                                    if (repeatPasswordText.equals(passwordText)) {
                                        if (
                                            mainViewModelRegister.checkAllValidations(
                                                textEmail = emailText,
                                                textPassword = passwordText,
                                                checkedStatePrivacyPolicies = checkedStatePrivacyPolicies
                                            )
                                        ) {
                                            mainViewModelRegister.createUserWithEmailAndPassword(
                                                email = emailText,
                                                password = passwordText,
                                                context = context,
                                                navController = navController
                                            )
                                            navController.navigate(Destinations.Login.route) {
                                                popUpTo(0)
                                            }
                                        }
                                        else {
                                            Toast.makeText(context,"Debes rellenar todos los campos correctamente",Toast.LENGTH_SHORT).show()
                                        }
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






