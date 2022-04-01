package me.saine.android.Views.MainAppActivity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.classmanegerandroid.Navigation.Destinations

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