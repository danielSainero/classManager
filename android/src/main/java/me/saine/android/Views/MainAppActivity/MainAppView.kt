package me.saine.android.Views.MainAppActivity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.classmanegerandroid.Navigation.Destinations
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import me.saine.android.Classes.CurrentUser
import me.saine.android.dataClasses.Course
import me.saine.android.dataClasses.Class

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
                        content = {
                            item {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Fbook.png?alt=media&token=8e4cc884-dcf1-41de-a0a0-4f1215bf67ff"
                                    ),
                                    contentDescription = "Logo",
                                    modifier = Modifier
                                        .height(250.dp)
                                        .width(450.dp)
                                        .padding(30.dp)
                                )
                            }
                            item {
                                Text(text = "Correo")
                            }
                            item {
                                Text(text = "Cursos:")
                                MyButton()
                            }
                            item {
                                //Todos los cursos
                            }
                            item {
                                Text(text = "Ajustes")
                            }
                        }
                    )
                }
            )
        },
        content = {

            Column(
                modifier = Modifier
                    .alpha(if(createItem) 0.2f else 1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                if(createItem) onValueChangeCreateItem(false)
                            }
                        )
                    }
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
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
                                )
                            }

                            itemsIndexed(CurrentUser.myCourses) { index, item ->
                                if (aplicateFilter.value)
                                if (item.name.lowercase().contains(filter)) {
                                    itemCourse(
                                        course = item.name,
                                        onClick = {navController.navigate("${Destinations.Course.route}/${item.id}")}
                                    )
                                }
                            }
                        }
                    )
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
                                )
                            }
                            itemsIndexed(CurrentUser.myClasses) { index, item ->
                                if (aplicateFilter.value)
                                if (item.name.lowercase().contains(filter)) {
                                    itemCourse(
                                        course = item.name,
                                        onClick = {navController.navigate("${Destinations.Class.route}/${item.id}")}
                                    )
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
fun createNewItem(
    navController: NavController,
    onValueChangeCreateItem: (Boolean) -> Unit
) {
    val miniFabSize = 40.dp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        content = {
            Text(
                text = "Crear curso",
                modifier = Modifier
                    .clickable { navController.navigate(Destinations.CreateCourse.route) }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.size(miniFabSize),
                content = {
                    Icon(
                        painter = rememberAsyncImagePainter(
                            model = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Fschool_white.png?alt=media&token=e393aacc-eb7e-45f3-8e9a-6e1eaefb7411"
                        ),
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                },
                onClick = {
                    onValueChangeCreateItem(false)
                    navController.navigate(Destinations.CreateCourse.route)
                }
            )
        }
    )

    Spacer(modifier = Modifier.padding(10.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        content = {
            Text(
                text = "Crear clase",
                modifier = Modifier
                    .clickable { navController.navigate(Destinations.CreateClass.route) }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.size(miniFabSize),
                content = {
                    Icon(
                        painter = rememberAsyncImagePainter(
                            model = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Fclass_white.png?alt=media&token=c3091fa8-b1b2-4969-90a8-1e2f09f3d856"
                        ),
                        contentDescription = "Class",
                        tint = Color.White
                    )
                },
                onClick = {
                    navController.navigate(Destinations.CreateClass.route)
                    onValueChangeCreateItem(false)
                }
            )
        }
    )
    Spacer(modifier = Modifier.padding(10.dp))
}




@Composable
fun MyButton() {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")) }

    Button(onClick = { context.startActivity(intent) }) {
        Text(text = "Navigate to Google!")
    }
}

