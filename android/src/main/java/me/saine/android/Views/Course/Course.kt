package me.saine.android.Views.Course

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import kotlinx.coroutines.launch
import me.saine.android.Classes.CurrentUser

@Composable
fun MainCourse(
    navController: NavController,
    mainViewModelCourse: MainViewModelCourse
) {
    val searchWidgetState by mainViewModelCourse.searchWidgetState
    val searchTextState by mainViewModelCourse.searchTextState

    Scaffold(
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    mainViewModelCourse.updateSearchTextState(newValue = it)
                    /*aplicateFilter.value = false
                    filter = it.lowercase()
                    aplicateFilter.value = true*/
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
                mainViewModelCourse = mainViewModelCourse
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
                            itemsIndexed(mainViewModelCourse.selectedClasses) { index: Int, item ->
                                itemCourse(
                                    course = item.name,
                                    onClick = {}
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
    mainViewModelCourse: MainViewModelCourse
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered,
                navController = navController,
                mainViewModelCourse = mainViewModelCourse
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}


@Composable
fun DefaultAppBar(
    onSearchClicked: () -> Unit,
    navController: NavController,
    mainViewModelCourse: MainViewModelCourse
) {
    val expanded = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text(text = "${mainViewModelCourse.selectedCourse.name}")
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
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
                                    Text(text = "Ver info")
                                }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                },
                                content = {
                                    Text(text = "Ver ayuda")
                                }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                },
                                content = {
                                    Text(text = "Añadir usuario")
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
                        Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            )
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search by name...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                color = Color.White
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primary,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            )
        )
    }
}