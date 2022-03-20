package me.saine.android.Views.Class

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.classmanegerandroid.Navigation.Destinations
import kotlinx.coroutines.NonDisposableHandle.parent
import me.saine.android.Views.Class.defaultAppBar
import me.saine.android.Views.Class.searchAppBar
import me.saine.android.Views.ViewsItems.confirmAlertDialog
import java.util.*


@Composable
fun MainClass(
    navController: NavController,
    mainViewModelClass: MainViewModelClass,
    classId: String
){
    val context = LocalContext.current
    val searchWidgetState by mainViewModelClass.searchWidgetState
    val searchTextState by mainViewModelClass.searchTextState
    val aplicateFilter = remember { mutableStateOf(true) }
    var filter: String = ""
    var (deleteItem,onValueChangeDeleteItem) = remember { mutableStateOf(false)}
    val getClass = remember { mutableStateOf(true) }

    if (getClass.value) {
        mainViewModelClass.getSelectedClass(classId)
        getClass.value = false
    }


    if (deleteItem) {
        var title = "¿Seguro que desea eliminar la clase seleccionado?"
        var subtitle = "Perderas todas las prácticas creadas. "

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteItem,
            onFinishAlertDialog = {
                if (it) {
                    mainViewModelClass.deleteClass(
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
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    mainViewModelClass.updateSearchTextState(newValue = it)
                    aplicateFilter.value = false
                    filter = it.lowercase()
                    aplicateFilter.value = true
                },
                onCloseClicked = {
                    mainViewModelClass.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {

                },
                onSearchTriggered = {
                    mainViewModelClass.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                },
                navController = navController,
                mainViewModelClass = mainViewModelClass,
                onValueChangeDeleteItem = onValueChangeDeleteItem
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {
                    navController.navigate(Destinations.CreateCourse.route)
                },
                content = {
                    Text(text = "+")
                }
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
                                itemsIndexed(mainViewModelClass.selectedPractices) { index: Int, item ->
                                    if (item.name.lowercase().contains(filter)) {
                                        itemPractice(
                                            course = item.name,
                                            onClick = {navController.navigate("${Destinations.Practice.route}/${item.id}")}
                                        )
                                    }
                                }
                            }
                            item {
                                var newActivity = remember{ mutableStateOf("") }
                                val newPractice = remember { mutableStateOf(false) }
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp, 4.dp)
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .clickable {
                                            //añadir
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
                                                if (newPractice.value) {
                                                    OutlinedTextField(
                                                        value = newActivity.value,
                                                        modifier = Modifier.fillMaxWidth(),
                                                        onValueChange = {
                                                            newActivity.value = it
                                                        },
                                                        placeholder = {
                                                            Text(text = "Escribe el nombre")
                                                        },
                                                        trailingIcon = {
                                                            Row(
                                                              content = {
                                                                  IconButton(
                                                                      onClick = {
                                                                        mainViewModelClass.createNewPractice(
                                                                            name = newActivity.value,
                                                                            navController = navController
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
                                                                          newPractice.value = false
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
                                                            newPractice.value = true
                                                        },
                                                        content = {
                                                            Text(
                                                                text = "Añadir práctica nueva",
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


@Composable
private fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    navController: NavController,
    mainViewModelClass: MainViewModelClass,
    onValueChangeDeleteItem: (Boolean) -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            defaultAppBar(
                onSearchClicked = onSearchTriggered,
                navController = navController,
                mainViewModelClass = mainViewModelClass,
                onValueChangeDeleteItem = onValueChangeDeleteItem
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