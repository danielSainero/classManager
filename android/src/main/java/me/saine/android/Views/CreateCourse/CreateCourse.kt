package me.saine.android.Views.CreateCourse

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import me.saine.android.Views.ViewsItems.createRowList
import me.saine.android.Views.ViewsItems.dropDownMenuWithAction

@Composable
fun MainCreateCourse(
    navController: NavController,
    mainViewModelCreateCourse: MainViewModelCreateCourse
) {
    //Texto
    val (textNameOfCourse, onValueChangeTextNameOfCourse) = remember { mutableStateOf("") }
    val (textOfDescription,onValueChangeTextOfDescription) = remember { mutableStateOf("") }


    var (addCourse,onValueChangeAddCourse) = remember { mutableStateOf(false) }


    //Variables de ayuda
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    if (addCourse) {
        addCourse(
            mainViewModelCreateCourse = mainViewModelCreateCourse,
            onValueChangeAddClasses = onValueChangeAddCourse
        )
    }

    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(text = "Creación de curso")
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
                                    value = textNameOfCourse,
                                    onValueChange = onValueChangeTextNameOfCourse,
                                    enable = true,
                                    KeyboardType = KeyboardType.Text
                                )
                            }
                            item {
                                createRowList(
                                    textOfRow = "Descripción",
                                    textOfLabel =  "Descripción",
                                    textOfPlaceholder = "Descripción del curso",
                                    value = textOfDescription,
                                    onValueChange = onValueChangeTextOfDescription,
                                    enable = true,
                                    KeyboardType = KeyboardType.Text
                                )
                            }
                            item {
                                dropDownMenuWithAction(
                                    textOfRow = "Clases",
                                    suggestions=  mainViewModelCreateCourse.getOfListTitle(),
                                    onClick = { onValueChangeAddCourse(true) }
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
                                    mainViewModelCreateCourse.createCourse(
                                        textOfDescription = textOfDescription,
                                        textNameOfCourse = textNameOfCourse,
                                        navController = navController,
                                        context = context
                                    )
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

@Composable
fun addCourse(
    mainViewModelCreateCourse: MainViewModelCreateCourse,
    onValueChangeAddClasses: (Boolean) -> Unit
) {
    Dialog(

        onDismissRequest = {
            onValueChangeAddClasses(false)
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .background(Color.White),
                content = {
                    itemsIndexed(mainViewModelCreateCourse.allListItems) { index, item ->
                        val isSelectedItem = remember { mutableStateOf(item.isSelected) }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                     if(item.isSelected) {
                                         item.isSelected = false
                                         isSelectedItem.value = false
                                    }
                                    else{
                                         item.isSelected = true
                                         isSelectedItem.value = true
                                    }
                                }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = item.title)
                            if(isSelectedItem.value) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color.Green,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    item {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            content = {
                                Text(text = "Aceptar")
                            },
                            onClick = {
                                onValueChangeAddClasses(false)
                            }
                        )
                    }
                }
            )
        }
    )
}