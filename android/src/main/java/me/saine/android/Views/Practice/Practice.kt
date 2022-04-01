package me.saine.android.Views.Practice;


import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import me.saine.android.Classes.CurrentUser
import me.saine.android.Views.ViewsItems.confirmAlertDialog
import me.saine.android.data.local.Message
import me.saine.android.data.network.AccesToDataBase.Companion.auth
import java.time.LocalDate.now


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPractice(
    navController: NavController,
    mainViewModelPractice: MainViewModelPractice,
    idPractice: String
) {
    val expanded = remember { mutableStateOf(false) }
    var commentsIsSelected by remember { mutableStateOf(true) }

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

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(800L)
            isRefreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { isRefreshing = true },
        content =  {
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
                        modifier = Modifier.fillMaxSize(),
                        content ={
                            if (commentsIsSelected) {
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
                            }


                            item {
                                Spacer(modifier = Modifier.padding(10.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(BorderStroke(1.dp, Color.LightGray)),
                                    content = {
                                        Spacer(modifier = Modifier.padding(1.dp))
                                    }
                                )
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier
                                        .clickable {
                                            commentsIsSelected = if (commentsIsSelected) false else true
                                        },
                                    content = {
                                        Text(text = "Comments")
                                    }
                                )

                                Spacer(modifier = Modifier.padding(8.dp))
                            }

                            itemsIndexed(mainViewModelPractice.chat.conversation){ index, item ->
                                Row(
                                    horizontalArrangement = if (auth.currentUser?.uid.toString().equals(item.sentBy.id)) Arrangement.End else Arrangement.Start,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    content = {

                                        Surface(
                                            modifier = Modifier
                                                .padding(8.dp, 4.dp)
                                                .fillMaxWidth(0.8f),
                                            shape = RoundedCornerShape(8.dp),
                                            elevation = 2.dp,
                                            content = {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(4.dp)
                                                        .fillMaxSize(),
                                                    content = {
                                                        Image(
                                                            painter = rememberAsyncImagePainter(
                                                                model = Uri.parse(CurrentUser.currentUser.imgPath)
                                                            ),
                                                            contentDescription = "avatar",
                                                            modifier = Modifier
                                                                .size(40.dp)
                                                                .clip(CircleShape)
                                                                .border(1.dp, Color.Gray, CircleShape)
                                                                .align(Alignment.CenterVertically),
                                                            contentScale = ContentScale.Crop,

                                                            )
                                                        Spacer(modifier = Modifier.padding(5.dp))
                                                        Column(
                                                            modifier = Modifier
                                                                .padding(4.dp)
                                                                .fillMaxHeight()
                                                                .fillMaxWidth(0.8f),
                                                            verticalArrangement = Arrangement.Center,
                                                            content = {
                                                                Text(
                                                                    text = item.message,
                                                                    style = MaterialTheme.typography.subtitle1,
                                                                    fontWeight = FontWeight.Bold,
                                                                )
                                                            }
                                                        )
                                                        Text(
                                                            text = "${now()}",
                                                            style = MaterialTheme.typography.caption,
                                                            modifier = Modifier
                                                                .padding(4.dp),
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
                        modifier = Modifier.fillMaxWidth(0.8f),
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
                                    sentOn = "${now().dayOfMonth}/${now().dayOfWeek}/${now().dayOfYear}"
                                )
                            )
                            mainViewModelPractice.updateChat()
                            onValueChageValue("")
                        }
                    )
                }
            )
        }
    )
}