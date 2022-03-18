package me.saine.android.Views.MainAppActivity

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun defaultAppBar(
    onSearchClicked: () -> Unit,
    scaffoldState: ScaffoldState
) {
    val expanded = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text(text = "Main App activity")
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch { scaffoldState.drawerState.open() }
                },
                content = {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            )
        }
    )
}