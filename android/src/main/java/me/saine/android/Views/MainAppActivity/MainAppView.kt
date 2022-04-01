package me.saine.android.Views.MainAppActivity

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.classmanegerandroid.Navigation.Destinations
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.delay
import me.saine.android.Classes.CurrentUser
import me.saine.android.data.network.AccesToDataBase.Companion.auth

private lateinit var database: DatabaseReference

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainAppView(
    navController: NavController,
    mainViewModelMainAppView: MainViewModelMainAppView
) {
    //Help variables
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val searchWidgetState by mainViewModelMainAppView.searchWidgetState
    val searchTextState by mainViewModelMainAppView.searchTextState
    val (createItem, onValueChangeCreateItem) = remember { mutableStateOf(false) }
    val aplicateFilter = remember { mutableStateOf(true) }
    var filter: String = ""
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val valueOfViewAppActivityState = remember { mutableStateOf("DEFAULT") }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current


    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(800L)
            isRefreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            isRefreshing = true
            mainViewModelMainAppView.clearSearchBar()
        },
        content =  {
            Scaffold (
                scaffoldState = scaffoldState,
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        onTextChange = {
                            mainViewModelMainAppView.updateSearchTextState(newValue = it)
                            aplicateFilter.value = false
                            filter = it.lowercase()
                            aplicateFilter.value = true
                        },
                        onCloseClicked = {
                            mainViewModelMainAppView.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {

                        },
                        onSearchTriggered = {
                            mainViewModelMainAppView.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        },
                        scaffoldState = scaffoldState
                    )
                },
                bottomBar = {
                    //BottomNavigation(navController = bottomNavController)
                },
                floatingActionButton = {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                        content = {
                            if (createItem) {
                                createNewItem(
                                    navController = navController,
                                    onValueChangeCreateItem = onValueChangeCreateItem
                                )
                            }
                            FloatingActionButton(
                                backgroundColor = MaterialTheme.colors.primary,
                                content = {
                                    Text(text = if (createItem) "-" else "+")
                                },
                                onClick = {
                                    onValueChangeCreateItem(if (createItem) false else true)
                                }
                            )
                        }
                    )
                },
                drawerContent = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(
                                        PaddingValues(
                                            start = 10.dp,
                                            end = 10.dp
                                        )
                                    ),
                                content = {
                                    item {
                                        Column(
                                            content = {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            PaddingValues(
                                                                top = 10.dp,
                                                                bottom = 10.dp
                                                            )
                                                        ),
                                                    content = {
                                                        Image(
                                                            painter = rememberAsyncImagePainter(
                                                                model = Uri.parse(CurrentUser.currentUser.imgPath)
                                                            ),
                                                            contentDescription = "avatar",
                                                            modifier = Modifier
                                                                .size(180.dp)
                                                                .clip(CircleShape)
                                                                .border(2.dp, Color.Gray, CircleShape)
                                                                .align(Alignment.CenterVertically),
                                                            contentScale = ContentScale.Crop,

                                                            )
                                                    }
                                                )
                                                Text(text = "${CurrentUser.currentUser.name.uppercase()}")
                                                Text(text = CurrentUser.currentUser.email)
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(30.dp)
                                                        .clickable {
                                                            clipboardManager.setText(AnnotatedString("${auth.currentUser?.uid}"))
                                                            Toast
                                                                .makeText(context, "Id del usuario copiada", Toast.LENGTH_SHORT)
                                                                .show()
                                                        },
                                                    content = {
                                                        Text(text = "#${auth.currentUser?.uid}")
                                                        Spacer(modifier = Modifier.padding(4.dp))

                                                        Icon(
                                                            painter = rememberAsyncImagePainter(
                                                                model = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Fcontent_copy_black.png?alt=media&token=bb9aba8d-74a7-4279-a59f-7adee22142ae"
                                                            ),
                                                            contentDescription = "Copiar"
                                                        )
                                                    }
                                                )

                                            }
                                        )
                                    }

                                    item {
                                        Spacer(modifier = Modifier.padding(4.dp))

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(BorderStroke(1.dp, Color.LightGray)),
                                            content = {
                                                Spacer(modifier = Modifier.padding(1.dp))
                                            }
                                        )


                                        val icon = if (expanded)
                                            Icons.Filled.KeyboardArrowUp
                                        else
                                            Icons.Filled.KeyboardArrowDown

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start,
                                            modifier = Modifier.fillMaxWidth(),
                                            content = {
                                                Text(text = "Cursos:")
                                                IconButton(
                                                    onClick = {
                                                        expanded = !expanded
                                                    },
                                                    content = {
                                                        Icon(
                                                            imageVector =  icon,
                                                            contentDescription = "arrowExpanded",
                                                        )
                                                    }
                                                )
                                            }
                                        )
                                        //MyButton()
                                    }
                                    if(expanded) {
                                        itemsIndexed(CurrentUser.myCourses) { index, item ->
                                            TextButton (

                                                onClick = {
                                                    navController.navigate("${Destinations.Course.route}/${item.id}")
                                                },
                                                content = {
                                                    Text(text = item.name)
                                                }
                                            )
                                        }
                                    }

                                    item {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(30.dp)
                                                .clickable {
                                                    navController.navigate(Destinations.Settings.route)
                                                },
                                            content = {
                                                Icon(
                                                    imageVector = Icons.Default.Settings,
                                                    contentDescription = "Ajustes"
                                                )
                                                Spacer(modifier = Modifier.padding(3.dp))
                                                Text(text = "Ajustes")
                                            }
                                        )
                                    }
                                    item {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(30.dp)
                                                .clickable {
                                                    auth.signOut()
                                                    navController.navigate(Destinations.Login.route) {
                                                        popUpTo(0)
                                                    }
                                                },
                                            content = {
                                                Icon(
                                                    painter = rememberAsyncImagePainter(
                                                        model = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Flogout_white.png?alt=media&token=888f2b85-0341-434f-9a7a-b7ddc8418c8b"
                                                    ),
                                                    contentDescription = "Cerrar sesión"
                                                )
                                                Spacer(modifier = Modifier.padding(3.dp))
                                                Text(text = "Cerrar sesión")
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                },
                content = {

                    Column(
                        modifier = Modifier
                            .alpha(if (createItem) 0.2f else 1f)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        if (createItem) onValueChangeCreateItem(false)
                                    }
                                )
                            }
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            if (!valueOfViewAppActivityState.value.equals("SELECTEDCLASS")) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(if (valueOfViewAppActivityState.value.equals("DEFAULT")) 0.5f else 1f)
                                        .border(BorderStroke(2.dp, Color.LightGray)),
                                    content = {
                                        stickyHeader {
                                            Text(
                                                text = "Mis cursos",
                                                textAlign = TextAlign.Center,
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                style = MaterialTheme.typography.caption,
                                                modifier = Modifier
                                                    .background(Color.White)
                                                    .padding(16.dp)
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        valueOfViewAppActivityState.value =
                                                            if (valueOfViewAppActivityState.value.equals("DEFAULT")) "SELECTEDCURSE" else "DEFAULT"
                                                    }
                                            )
                                        }

                                        itemsIndexed(CurrentUser.myCourses) { index, item ->
                                            if (aplicateFilter.value)
                                                if (item.name.lowercase().contains(filter)) {
                                                    itemCourse(
                                                        title = item.name,
                                                        subtitle = "${item.classes.size}",
                                                        onClick = {navController.navigate("${Destinations.Course.route}/${item.id}")}
                                                    )
                                                }
                                        }
                                    }
                                )
                            }
                            if (!valueOfViewAppActivityState.value.equals("SELECTEDCURSE")) {

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .border(BorderStroke(2.dp, Color.LightGray)),
                                    content = {
                                        stickyHeader {
                                            Text(
                                                text = "Mis Clases",
                                                textAlign = TextAlign.Center,
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                style = MaterialTheme.typography.caption,
                                                modifier = Modifier
                                                    .background(Color.White)
                                                    .padding(16.dp)
                                                    .fillMaxWidth()
                                                    .clickable {


                                                        valueOfViewAppActivityState.value =
                                                            if (valueOfViewAppActivityState.value.equals("DEFAULT")) "SELECTEDCLASS" else "DEFAULT"

                                                    }
                                            )
                                        }
                                        itemsIndexed(CurrentUser.myClasses) { index, item ->
                                            if (aplicateFilter.value)
                                                if (item.name.lowercase().contains(filter)) {
                                                    itemClass(
                                                        title = item.name,
                                                        subtitle = "${item.idPractices.size}",
                                                        onClick = { navController.navigate("${Destinations.Class.route}/${item.id}") }
                                                    )
                                                }
                                        }
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
    scaffoldState: ScaffoldState
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            defaultAppBar(
                onSearchClicked = onSearchTriggered,
                scaffoldState = scaffoldState
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




@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        "Home",
        "Classes",
        "Curses",
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.Black,
        content = {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                   },
                label = { Text(text = "Home", fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = true,
                onClick = {}
            )
        }
    )
}


@Composable
fun MyButton() {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")) }

    Button(onClick = { context.startActivity(intent) }) {
        Text(text = "Navigate to Google!")
    }
}

