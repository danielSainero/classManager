package com.example.classmanegerandroid.Views.Login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.compose.rememberImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavController
import androidx.navigation.navArgument
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.classmanegerandroid.Navigation.Destinations
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.appinvite.FirebaseAppInvite
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.socialMetaTagParameters
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import me.saine.android.R.drawable
import me.saine.android.R.string.default_web_client_id
import me.saine.android.Views.Login.bigPasswordInput


@Composable
fun MainLogin(
    navController: NavController,
    mainViewModelLogin: MainViewModelLogin
) {

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(800L)
            isRefreshing = false
        }
    }



    val context = LocalContext.current
    val activity = context as Activity
    val loginWithGoogle = remember { mutableStateOf(false) }



    if (loginWithGoogle.value){
        signInWithGoogle(
            activity = activity
        )
        loginWithGoogle.value = false
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { isRefreshing = true },
        content =  {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Login")
                        },
                    )
                },
                content = {

                    //Texts
                    val emailText = remember{ mutableStateOf("test@gmail.com") }
                    val (passwordText,onValueChangePasswordText) = remember{ mutableStateOf("11111111") }
                    var (passwordError,onValueChangePasswordError) = remember { mutableStateOf(false) }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize(),
                        content = {

                            LazyColumn(
                                content = {

                                    item {
                                        Image(
                                            painter = rememberImagePainter(
                                                data = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Flogo.png?alt=media&token=a9d234bf-a791-44cd-ad29-fd18b54f488e"
                                            ),
                                            contentDescription = "Logo",
                                            modifier = Modifier
                                                .height(250.dp)
                                                .width(450.dp)
                                                .padding(30.dp)
                                                .clickable {
                                                    //navController.navigate(Destinations.MainAppView.route)
                                                    //generateContentLink()
                                                    crateDynamicLink()
                                                }
                                        )
                                    }

                                    item {
                                        OutlinedTextField(
                                            value = emailText.value,
                                            onValueChange = {
                                                emailText.value = it
                                            },
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                focusedBorderColor = Color.Gray,
                                                unfocusedBorderColor = Color.LightGray
                                            ),
                                            placeholder = { Text("Email") },
                                            singleLine = true,
                                            label = { Text(text = "Email") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        )
                                    }

                                    item {

                                        bigPasswordInput(
                                            value = passwordText,
                                            onValueChangeValue = onValueChangePasswordText,
                                            valueError = passwordError,
                                            onValueChangeError = onValueChangePasswordError,
                                            validateError = mainViewModelLogin::isValidPassword
                                        )
                                    }
                                    item {
                                        Button(
                                            content = {
                                                Text(text = "Iniciar sesión")
                                            },
                                            onClick = {

                                                mainViewModelLogin.signIn(
                                                    email = emailText.value,
                                                    password =passwordText,
                                                    mainViewModelLogin = mainViewModelLogin,
                                                    context = context,
                                                    navController = navController
                                                )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        )
                                    }
                                    item {
                                        Text(
                                            text = "O bien",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        )
                                    }
                                    item {
                                        Button(
                                            content = {
                                                Text(text = "Iniciar sesión con Google")
                                            },
                                            onClick = {
                                                //Comprobar inicio de sesión con google
                                                loginWithGoogle.value = true

                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        )
                                    }
                                    item {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceAround,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(start = 40.dp, end = 40.dp))
                                        ) {
                                            Text(
                                                text = "He olvidado la contraseña",
                                                color = Color.Blue,
                                                modifier = Modifier
                                                    .clickable {
                                                        navController.navigate(Destinations.ForgotPassword.route)
                                                    }
                                            )
                                            Text(
                                                text = "Crear usuario",
                                                color = Color.Blue,
                                                modifier = Modifier
                                                    .clickable {
                                                        navController.navigate(Destinations.Register.route)
                                                    }
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    )

                },
            )

        }
    )
}

fun generateContentLink(): Uri {
    val baseUrl = Uri.parse("https://classManager.page.link")
    val domain = "https://classManager.page.link"

    val link = FirebaseDynamicLinks
        .getInstance()
        .createDynamicLink()
        .setLink(baseUrl)
        .setDomainUriPrefix(domain)
        .setIosParameters(DynamicLink.IosParameters.Builder("com.your.bundleid").build())
        .setAndroidParameters(DynamicLink.AndroidParameters.Builder("me.saine.android.Views.Register").build())
        .buildDynamicLink()

    return link.uri
}

/*
private fun onShareClicked() {
    val link = DynamicLinksUtil.generateContentLink()

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, link.toString())

    startActivity(Intent.createChooser(intent, "Share Link"))
}
*/

fun crateDynamicLink() {
    val dynamicLink = Firebase.dynamicLinks.dynamicLink {
        link = Uri.parse("https://www.classManager.com/")
        domainUriPrefix = "https://classManager.page.link"
        // Open links with this app on Android
        androidParameters("me.saine.android.Views.Register") {
            minimumVersion = 125
        }
        socialMetaTagParameters {
            title = "Example of a Dynamic Link"
            description = "This link works whether the app is installed or not!"
        }
    }

    val dynamicLinkUri = dynamicLink.uri
}











@Composable
fun signInWithGoogle(
    activity: Activity
){
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    googleSignInClient.signOut()

    signInGoogle(
        googleSignInClient = googleSignInClient,
        activity = activity
    )
}

private fun signInGoogle(
    googleSignInClient: GoogleSignInClient,
    activity: Activity
) {
    val signInIntent = googleSignInClient.signInIntent
    activity.startActivityForResult(signInIntent, 1)
}


