package me.saine.android.Views.Settings.MyAccountOptions

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import me.saine.android.Classes.CurrentUser
import me.saine.android.Views.ViewsItems.confirmAlertDialog
import me.saine.android.data.network.AccesToDataBase.Companion.auth

@Composable
fun MainMyAccountOptions(
    navController: NavController
) {
    val context = LocalContext.current
    var (deleteItem,onValueChangeDeleteItem) = remember { mutableStateOf(false)}

    if (deleteItem) {
        confirmAlertDialog(
            title = "¿Estas seguro que desea eliminar su cuenta?",
            subtitle = "No podrás volver a recuperarla",
            onValueChangeGoBack = onValueChangeDeleteItem,
            onFinishAlertDialog = {
                  if (it) {
                      auth.currentUser!!.delete()
                          .addOnCompleteListener{ task ->
                              if (task.isSuccessful) {
                                  Toast.makeText(context,"La cuenta ha sido eliminada correctamente", Toast.LENGTH_SHORT).show()
                                  navController.navigate(Destinations.Login.route) {
                                      popUpTo(0)
                                  }
                              }
                          }
                  }
                onValueChangeDeleteItem(false)
            } ,
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Cuenta")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        content = {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    )
                }
            )
        },
        content = {
            LazyColumn(
                content ={
                    item {
                        Row(
                            content = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .clickable {
                                            navController.navigate(Destinations.PrivacyPolicies.route)
                                        },
                                    content = {
                                        Spacer(modifier = Modifier.padding(5.dp))
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Perfil"
                                        )
                                        Spacer(modifier = Modifier.padding(3.dp))
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f),
                                            content = {
                                                Text(text = "Politicas de privacidad")
                                            }
                                        )
                                    }
                                )
                            }
                        )
                    }
                    item {
                        Row(
                            content = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .clickable {

                                        },
                                    content = {
                                        Spacer(modifier = Modifier.padding(5.dp))
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Perfil"
                                        )
                                        Spacer(modifier = Modifier.padding(3.dp))
                                        Column(
                                            modifier = Modifier.fillMaxWidth(0.9f),
                                            content = {
                                                Text(text = CurrentUser.currentUser.email)
                                            }
                                        )
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit user email",
                                        )
                                    }
                                )
                            }
                        )
                    }
                    item {
                        Row(
                            content = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .clickable {
                                            onValueChangeDeleteItem(true)

                                        },
                                    content = {
                                        Spacer(modifier = Modifier.padding(5.dp))
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Descripción del perfil"
                                        )
                                        Spacer(modifier = Modifier.padding(3.dp))
                                        Column(
                                            modifier = Modifier.fillMaxWidth(0.9f),
                                            content = {
                                                Text(
                                                    text = "Delete my acount"
                                                )
                                            }
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            )
        }
    )
}