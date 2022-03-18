package me.saine.android.Views.Class

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import kotlinx.coroutines.NonDisposableHandle.parent
import me.saine.android.Views.Class.defaultAppBar
import me.saine.android.Views.Class.searchAppBar
import java.util.*


@Composable
fun MainClass(
    navController: NavController,
    mainViewModelClass: MainViewModelClass
){
    val context = LocalContext.current
    val searchWidgetState by mainViewModelClass.searchWidgetState
    val searchTextState by mainViewModelClass.searchTextState
    val aplicateFilter = remember { mutableStateOf(true) }
    var filter: String = ""

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
                mainViewModelClass = mainViewModelClass
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
    mainViewModelClass: MainViewModelClass
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            defaultAppBar(
                onSearchClicked = onSearchTriggered,
                navController = navController,
                mainViewModelClass = mainViewModelClass
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