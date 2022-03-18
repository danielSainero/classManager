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
    val aplicateFilter = remember { mutableStateOf(true) }
    var filter: String = ""

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
                            if (aplicateFilter.value) {
                                itemsIndexed(mainViewModelCourse.selectedClasses) { index: Int, item ->
                                    if (item.name.lowercase().contains(filter)) {
                                        itemCourse(
                                            course = item.name,
                                            onClick = {}
                                        )
                                    }
                                }
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
            defaultAppBar(
                onSearchClicked = onSearchTriggered,
                navController = navController,
                mainViewModelCourse = mainViewModelCourse
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



