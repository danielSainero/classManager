package me.saine.android.Views.MainAppActivity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import me.saine.android.Classes.CurrentUser
import me.saine.android.dataClasses.Course
import me.saine.android.dataClasses.Class

private lateinit var database: DatabaseReference

@Composable
fun MainAppView(
    navController: NavController,
    mainViewModelMainAppView: MainViewModelMainAppView
) {
    //Help variables
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val searchWidgetState by mainViewModelMainAppView.searchWidgetState
    val searchTextState by mainViewModelMainAppView.searchTextState
    val createItem = remember { mutableStateOf(false) }



    val context = LocalContext.current
    val allCourses = remember { mutableListOf<Course>() }
    val allClasses = remember { mutableListOf<Class>() }
    var (generateCourses,onValueChangeGenerateCouses) = remember { mutableStateOf(false) }
    getCourses(mainViewModelMainAppView,onValueChangeGenerateCouses)
    /*val database = Firebase.database
    val myRef = database.getReference("users")*/

    //Firebase
    //database = Firebase.database.reference

    Scaffold (
        scaffoldState = scaffoldState,
        topBar = {
             MainAppBar(
                 searchWidgetState = searchWidgetState,
                 searchTextState = searchTextState,
                 onTextChange = {
                     mainViewModelMainAppView.updateSearchTextState(newValue = it)
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
                    if (createItem.value) {
                        val miniFabSize = 40.dp

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "Crear clase")
                            Spacer(modifier = Modifier.padding(5.dp))
                            FloatingActionButton(
                                backgroundColor = MaterialTheme.colors.primary,
                                modifier = Modifier.size(miniFabSize),
                                content = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Search Icon",
                                        tint = Color.White
                                    )
                                },
                                onClick = {
                                    createItem.value = false
                                }
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "Crear curso")
                            Spacer(modifier = Modifier.padding(5.dp))
                            FloatingActionButton(
                                backgroundColor = MaterialTheme.colors.primary,
                                modifier = Modifier.size(miniFabSize),
                                content = {
                                   /* Icon(
                                        painter = mainViewModelMainAppView.storage.reference.child("appImage/${}"),
                                        contentDescription = "Search Icon",
                                        tint = Color.White
                                    )*/
                                },
                                onClick = {
                                    createItem.value = false
                                }
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }

                    FloatingActionButton(
                        backgroundColor = MaterialTheme.colors.primary,
                        content = {
                            Text(text = "+")
                        },
                        onClick = {
                            createItem.value = true
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
                    .alpha(if(createItem.value) 0.2f else 1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                if(createItem.value) createItem.value = false
                            }
                        )
                    }
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    LazyColumn(
                        content = {
                            item {
                                Text(text = "Mis cursos")
                            }

                            itemsIndexed(CurrentUser.myCourses) { index, item ->
                                itemCourse(
                                    course = item.name,
                                    navController = navController
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    LazyColumn(
                        content = {
                            item {
                                Text(text = "Mis clases")
                            }
                            itemsIndexed(CurrentUser.myClasses) { index, item ->
                                itemCourse(
                                    course = item.name,
                                    navController = navController
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
            DefaultAppBar(
                onSearchClicked = onSearchTriggered,
                scaffoldState = scaffoldState
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
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch { scaffoldState.drawerState.open() }
                }
            ) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "",
                    tint = Color.White
                )
            }
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
            textStyle = androidx.compose.ui.text.TextStyle(
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

@Composable
fun getCourses(
    mainViewModelMainAppView: MainViewModelMainAppView,
    generateCourses: (Boolean) -> Unit
) {
    mainViewModelMainAppView.db.collection("course").get().addOnSuccessListener {
        mainViewModelMainAppView.allCourses.clear()
        for (document in it) {
            /* if (auth.currentUser?.uid.equals(document.id)) {}*/
            mainViewModelMainAppView.allCourses.add(
                Course(
                    name = document.get("name") as String,
                    classes = document.get("classes") as MutableList<String>,
                    admins = document.get("admins") as MutableList<String>,
                )
            )
        }
        getMyCourses(mainViewModelMainAppView = mainViewModelMainAppView)
        generateCourses(true)
    }
}

fun getMyCourses(mainViewModelMainAppView: MainViewModelMainAppView) {
    mainViewModelMainAppView.myCourses.clear()
    mainViewModelMainAppView.allCourses.forEach{
        it.admins.forEach{ admins ->
            if (admins.equals(mainViewModelMainAppView.auth.currentUser?.uid.toString())) {
                mainViewModelMainAppView.myCourses.add(it)
            }
        }
    }
}

@Composable
fun MyButton() {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")) }

    Button(onClick = { context.startActivity(intent) }) {
        Text(text = "Navigate to Google!")
    }
}


/*
fun getClasses(
    mainViewModelMainAppView: MainViewModelMainAppView,
    allClasses: MutableList<Class>
) {
    mainViewModelMainAppView.db.collection("course").get().addOnSuccessListener {
        for (document in it) {
            allClasses.add(
                Class(
                    name = document.get("name") as String,
                )
            )
        }
    }
}*/