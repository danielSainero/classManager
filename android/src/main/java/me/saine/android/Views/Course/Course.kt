package me.saine.android.Views.Course

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.classmanegerandroid.Navigation.Destinations
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saine.android.Classes.CurrentUser
import me.saine.android.Views.ViewsItems.confirmAlertDialog

@Composable
fun MainCourse(
    navController: NavController,
    mainViewModelCourse: MainViewModelCourse,
    courseId: String
) {

    var (deleteItem,onValueChangeDeleteItem) = remember { mutableStateOf(false)}
    val searchWidgetState by mainViewModelCourse.searchWidgetState
    val searchTextState by mainViewModelCourse.searchTextState
    val aplicateFilter = remember { mutableStateOf(true) }
    var filter: String = ""
    val context = LocalContext.current

    val getCourse = remember { mutableStateOf(true) }
    if (getCourse.value) {
        mainViewModelCourse.getSelectedCourse(courseId)
        getCourse.value = false
    }

    val (IdOfUser,onValueChangeIdOfUser) = remember { mutableStateOf("") }
    val (addNewUser,onValueChangeAddNewUser) = remember { mutableStateOf(false) }
    val (getMoreInformation,onValueChangeGetMoreInformation) = remember { mutableStateOf(false) }
    val (textSelectedItem,onValueChangeTextSelectedItem) = remember { mutableStateOf("") }

    if (getMoreInformation) {

    }

    if (addNewUser) {
        addNewUser(
            onValueCloseDialog = onValueChangeAddNewUser,
            onValueChangeIdOfUser = onValueChangeIdOfUser,
            onValueChangeTextSelectedItem = onValueChangeTextSelectedItem,
            value = IdOfUser,
            label = "Id del usuario",
            placeholder = "Añadir la Id de un usuario",
            onClickSave = {
                mainViewModelCourse.addNewUser(IdOfUser, context, textSelectedItem)
                onValueChangeAddNewUser(false)
            }
        )
    }


    if (deleteItem) {
        var title: String = "¿Seguro que desea eliminar el Curso seleccionado?"
        var subtitle: String = ""
        if (mainViewModelCourse.selectedClasses.size == 0)
           subtitle = "Este curso no contiene ninguna clase"
        else
            subtitle = "Este curso contiene ${mainViewModelCourse.selectedClasses.size} clases, se eliminarán también. "

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteItem,
            onFinishAlertDialog = {
                if (it) {
                    mainViewModelCourse.deleteCurse(
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
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        onTextChange = {
                            mainViewModelCourse.updateSearchTextState(newValue = it)
                            aplicateFilter.value = false
                            filter = it.lowercase()
                            aplicateFilter.value = true
                        },
                        onCloseClicked = {
                            mainViewModelCourse.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            /*aplicateFilter.value = false
                            filter = it.lowercase()
                            aplicateFilter.value = true*/
                        },
                        onSearchTriggered = {
                            mainViewModelCourse.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        },
                        navController = navController,
                        mainViewModelCourse = mainViewModelCourse,
                        onValueChangeDeleteItem = onValueChangeDeleteItem,
                        onValueChangeAddNewUser = onValueChangeAddNewUser,
                        onValueChangeGetMoreInformation = onValueChangeGetMoreInformation
                    )
                },
                content = {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                content = {
                                    if (aplicateFilter.value) {
                                        itemsIndexed(mainViewModelCourse.selectedClasses) { index: Int, item ->
                                            if (item.name.lowercase().contains(filter)) {
                                                itemCourse(
                                                    course = item.name,
                                                    onClick = {navController.navigate("${Destinations.Class.route}/${item.id}")}
                                                )
                                            }
                                        }
                                    }
                                    item {
                                        var newClass = remember{ mutableStateOf("") }
                                        val newItem = remember { mutableStateOf(false) }
                                        Card(
                                            modifier = Modifier
                                                .padding(8.dp, 4.dp)
                                                .fillMaxWidth()
                                                .height(70.dp)
                                                .clickable {
                                                },
                                            shape = RoundedCornerShape(8.dp),
                                            elevation = 4.dp,
                                            content = {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(4.dp)
                                                        .fillMaxSize(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    content = {
                                                        if (newItem.value) {
                                                            OutlinedTextField(
                                                                value = newClass.value,
                                                                modifier = Modifier.fillMaxWidth(),
                                                                onValueChange = {
                                                                    newClass.value = it
                                                                },
                                                                placeholder = {
                                                                    Text(text = "Escribe el nombre")
                                                                },
                                                                trailingIcon = {
                                                                    Row(
                                                                        content = {
                                                                            IconButton(
                                                                                onClick = {
                                                                                    mainViewModelCourse.createNewClass(
                                                                                        navController = navController,
                                                                                        context = context,
                                                                                        textDescription = "",
                                                                                        textNameOfClass = newClass.value,
                                                                                        itemSelectedCurse = mainViewModelCourse.selectedCourse
                                                                                    )
                                                                                },
                                                                                content = {
                                                                                    Icon(
                                                                                        imageVector = Icons.Default.Check,
                                                                                        contentDescription = "Create Practice",
                                                                                    )
                                                                                }
                                                                            )
                                                                            IconButton(
                                                                                onClick = {
                                                                                    newItem.value = false
                                                                                },
                                                                                content = {
                                                                                    Icon(
                                                                                        imageVector = Icons.Default.Close,
                                                                                        contentDescription = "Create Practice",
                                                                                    )
                                                                                }
                                                                            )
                                                                        }
                                                                    )
                                                                }
                                                            )
                                                        } else {
                                                            TextButton(
                                                                onClick = {
                                                                    newItem.value = true
                                                                },
                                                                content = {
                                                                    Text(
                                                                        text = "Crear nueva clase",
                                                                        fontSize = 18.sp
                                                                    )
                                                                }
                                                            )
                                                        }
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
    )
}


@Composable
private fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    navController: NavController,
    mainViewModelCourse: MainViewModelCourse,
    onValueChangeDeleteItem: (Boolean) -> Unit,
    onValueChangeAddNewUser: (Boolean) -> Unit,
    onValueChangeGetMoreInformation: (Boolean) -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            defaultAppBar(
                onSearchClicked = onSearchTriggered,
                navController = navController,
                mainViewModelCourse = mainViewModelCourse,
                onValueChangeDeleteItem = onValueChangeDeleteItem,
                onValueChangeAddNewUser = onValueChangeAddNewUser,
                onValueChangeGetMoreInformation = onValueChangeGetMoreInformation,
            )
        }
        SearchWidgetState.OPENED -> {
            searchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}



