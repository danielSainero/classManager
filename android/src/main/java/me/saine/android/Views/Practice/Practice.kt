package me.saine.android.Views.Practice

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.classmanegerandroid.Navigation.Destinations
import me.saine.android.Classes.CurrentUser
import me.saine.android.Views.ViewsItems.confirmAlertDialog
import me.saine.android.dataClasses.Message
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPractice(
    navController: NavController,
    mainViewModelPractice: MainViewModelPractice,
    idPractice: String
) {
    val expanded = remember { mutableStateOf(false) }
    val context = LocalContext.current
    var (deleteItem,onValueChangeDeleteItem) = remember { mutableStateOf(false)}

    val (textDescription, onValueChangeTextDescription) = remember { mutableStateOf("") }
    val (textDate,onValueChangeTextDate) = remember { mutableStateOf("") }
    val (textAnnotation,onValueChangeTextAnnotation) = remember { mutableStateOf("") }
    val (textMessage,onValueChangeTextMessage) = remember { mutableStateOf("") }
    val getPractice = remember { mutableStateOf(true) }

    if(getPractice.value) {
        mainViewModelPractice.getPractice(idPractice = idPractice)
        getPractice.value = false
    }


    if (deleteItem) {
        var title = "¿Seguro que desea eliminar la prácica seleccionada?"
        var subtitle = "No podrás volver a recuperarla"

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteItem,
            onFinishAlertDialog = {
                if (it) {
                    mainViewModelPractice.deletePractice(
                        context = context,
                        navController = navController
                    )
                }
                onValueChangeDeleteItem(false)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = mainViewModelPractice.selectedPractice.name)
                },
                actions = {
                    Box (
                        modifier = Modifier
                            .wrapContentSize(),
                        content = {
                            IconButton(
                                onClick = { expanded.value = true },
                                content = {
                                    Icon(
                                        Icons.Filled.MoreVert,
                                        contentDescription = "Localized description",
                                        tint = Color.White
                                    )
                                }
                            )

                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false },
                                content = {
                                    DropdownMenuItem(
                                        onClick = {
                                            expanded.value = false
                                        },
                                        content = {
                                            Text(text = "Ver miembros")
                                        }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            expanded.value = false
                                            onValueChangeDeleteItem(true)
                                        },
                                        content = {
                                            Text(
                                                text = "Eliminar actividad",
                                                color = Color.Red
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Ir atras",
                                tint = Color.White
                            )
                        }
                    )
                }
            )
        },
        backgroundColor = Color.White,
        bottomBar = {

            bottomAppBar(
                value = textMessage,
                onValueChageValue = onValueChangeTextMessage,
                mainViewModelPractice = mainViewModelPractice
            )

        },
        content = {
            LazyColumn(
                content ={
                    item {
                        showDatePicker(
                            context = context,
                            textDate = textDate,
                            onValueChangeTextDate = onValueChangeTextDate,
                            label = "Fecha de entrega",
                            placeholder = "Fecha de entrega"
                        )
                    }
                    item {
                        bigTextField(
                            text = "Descripción",
                            value = textDescription,
                            onValueChange = onValueChangeTextDescription,
                            KeyboardType = KeyboardType.Text
                        )
                    }

                    item {
                        bigTextField(
                            text = "Annotation",
                            value = textAnnotation,
                            onValueChange = onValueChangeTextAnnotation,
                            KeyboardType = KeyboardType.Text
                        )
                    }

                    item {
                        Text(text = "Comments")
                    }

                    itemsIndexed(mainViewModelPractice.chat.conversation){ index, item ->  
                        Text(text = item.message)
                    }

                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun bottomAppBar(
    value: String,
    onValueChageValue: (String) -> Unit,
    mainViewModelPractice: MainViewModelPractice
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Transparent),
        elevation = AppBarDefaults.BottomAppBarElevation,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                content = {

                    OutlinedTextField(
                        modifier = Modifier,
                        value = value,
                        shape = RoundedCornerShape(30.dp),
                        onValueChange = {
                            onValueChageValue(it)
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .alpha(ContentAlpha.medium),
                                text = "Message",
                                color = Color.Black
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = MaterialTheme.typography.subtitle1.fontSize,
                            color = Color.Black
                        ),
                        singleLine = false,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            //Darle al botón de enviar del teclado
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            cursorColor = Color.Black
                        )
                    )

                    FloatingActionButton(
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(50.dp),
                        content = {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Enviar mensaje",
                                tint = Color.White
                            )
                        },
                        onClick = {
                            mainViewModelPractice.chat.conversation.add(
                                Message(
                                    message = value,
                                    sentBy = CurrentUser.currentUser,
                                    sentOn = "${now().dayOfYear}/${now().dayOfMonth}/${now().dayOfWeek}"

                                )
                            )
                        }
                    )
                }
            )
        }
    )
}