package me.saine.android.Views.Settings


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.classmanegerandroid.Navigation.Destinations
import me.saine.android.Classes.CurrentUser


@Composable
fun MainSettings(
    navController: NavController
) {
    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(text = "Ajustes")
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
        },
        content = {
            LazyColumn(
                content ={
                    item {
                       itemSetting(
                           title = CurrentUser.currentUser.name,
                           subtitle = CurrentUser.currentUser.email,
                           urlImage = CurrentUser.currentUser.imgPath,
                           onClick = {
                               navController.navigate(Destinations.MyAccount.route)
                           }
                       )
                    }
                    item {
                       itemSetting(
                           title = "Mi cuenta",
                           subtitle = "Cerrar Sesión, Privacidad...",
                           urlImage = CurrentUser.currentUser.imgPath,
                           onClick = {
                               navController.navigate(Destinations.MyAccountOptions.route)
                           }
                       )
                    }
                }
            )
        }
    )


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}