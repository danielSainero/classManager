package me.saine.android.Views.CreateClass

import me.saine.android.Views.CreateCourse.MainViewModelCreateCourse

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import me.saine.android.Classes.CurrentUser
import me.saine.android.Classes.CurrentUser.Companion.auth
import me.saine.android.Classes.CurrentUser.Companion.db
import me.saine.android.Views.ViewsItems.createRowList
import me.saine.android.Views.ViewsItems.dropDownMenuWithAction
import me.saine.android.Views.ViewsItems.selectedDropDownMenu
import me.saine.android.dataClasses.Course
import me.saine.android.dataClasses.ListItem

@Composable
fun MainCreateClass(
    navController: NavController,
    mainViewModelCreateClass: MainViewModelCreateClass
) {
    //Texto
    val (textNameOfClass,onValueChangeTextNameOfClass) = remember { mutableStateOf("") }
    val (textDescription,onValueChangeTextDescription) = remember { mutableStateOf("") }
    val (itemSelectedCurse,onValueChangeItemSelectedCurse) = remember { mutableStateOf<Course>(Course(arrayListOf(), arrayListOf(),"Sin asignar","","Sin asignar")) }


    var (addClasses,onValueChangeaddClasses) = remember { mutableStateOf(false) }


    //Variables de ayuda
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Creación de clase")
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
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f),
                        content = {
                            item {
                                createRowList(
                                    textOfRow = "Nombre",
                                    textOfLabel =  "Nombre",
                                    textOfPlaceholder = "Nombre de la clase",
                                    value = textNameOfClass,
                                    onValueChange = onValueChangeTextNameOfClass,
                                    enable = true,
                                    KeyboardType = KeyboardType.Text
                                )
                            }
                            item {
                                createRowList(
                                    textOfRow = "Descripción",
                                    textOfLabel =  "Descripción",
                                    textOfPlaceholder = "Descripción de la clase",
                                    value = textDescription,
                                    onValueChange = onValueChangeTextDescription,
                                    enable = true,
                                    KeyboardType = KeyboardType.Text
                                )
                            }
                            item {
                                selectedDropDownMenuCurseItem(
                                    textOfRow = "Curso",
                                    suggestions = CurrentUser.myCourses,
                                    onValueChangeTextSelectedItem = onValueChangeItemSelectedCurse
                                )
                            }
                        }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp, end = 10.dp),
                        content = {
                            Button(
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    top = 6.dp,
                                    end = 10.dp,
                                    bottom = 6.dp
                                ),
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 20.dp),
                                onClick = {
                                    navController.popBackStack()
                                },
                                content = {
                                    Text(text = "Cancelar")
                                }
                            )
                            Button(
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    top = 6.dp,
                                    end = 10.dp,
                                    bottom = 6.dp
                                ),
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 20.dp),
                                onClick = {

                                    val document = db.collection("classes").document()
                                    val idOfDocument = document.id
                                    document.set(
                                        hashMapOf(
                                            "admins" to  mutableListOf("${auth.currentUser?.uid}"),
                                            "idPractices" to mutableListOf<String>(),
                                            "description" to textDescription,
                                            "name" to textNameOfClass,
                                            "idOfCourse" to itemSelectedCurse.id
                                        )
                                    ).addOnSuccessListener {
                                        if(!itemSelectedCurse.name.equals("Sin asignar") ) {
                                            itemSelectedCurse.classes.add(idOfDocument)
                                            db.collection("course")
                                                .document(itemSelectedCurse.id)
                                                .set(itemSelectedCurse)
                                        }

                                        CurrentUser.currentUser.classes.add(idOfDocument)

                                        db.collection("users")
                                            .document(auth.currentUser?.uid.toString())
                                            .set(CurrentUser.currentUser)
                                            .addOnSuccessListener {
                                                CurrentUser.updateDates()
                                                Toast.makeText(context,"El curso ha sido creado correctamente",Toast.LENGTH_SHORT).show()
                                                navController.navigate(Destinations.MainAppView.route)
                                            }
                                    }
                                },
                                content = {
                                    Text(text = "Crear")
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}
