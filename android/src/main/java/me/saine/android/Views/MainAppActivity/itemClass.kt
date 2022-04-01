package me.saine.android.Views.MainAppActivity

import android.support.v4.os.IResultReceiver.Default
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation

@Composable
fun itemClass(
    title: String,
    subtitle: String,
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
                content = {
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
                        content = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.padding(4.dp))

                            Row (//Email
                                content = {
                                    Icon(
                                        painter = rememberAsyncImagePainter(
                                            model = "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Ftask_black.png?alt=media&token=a3898a4b-d7a1-41e8-9c78-268f7d0ebf03"
                                        ),
                                        contentDescription = "Comments",
                                        modifier = Modifier.size(21.dp)
                                    )
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.caption,
                                        modifier = Modifier
                                            .padding(4.dp),
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}