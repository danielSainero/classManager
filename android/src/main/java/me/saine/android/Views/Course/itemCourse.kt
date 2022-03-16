package me.saine.android.Views.Course

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation

@Composable
fun itemCourse(
    course: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(90.dp)
            .clickable {
                //Navegación
                onClick()
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        content = {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = "https://www.psicoactiva.com/wp-content/uploads/puzzleclopedia/Libros-codificados-300x262.jpg",
                        builder = {
                            scale(Scale.FILL)
                            //placeholder(R.drawable.notification_action_background)
                            transformations(CircleCropTransformation())
                        },
                    ),
                    contentDescription = "Imágen del curso",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f),
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = course,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "y",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(4.dp),
                    )
                }
            }
        }
    )
}