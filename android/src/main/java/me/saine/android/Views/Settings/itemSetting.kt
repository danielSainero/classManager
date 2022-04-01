package me.saine.android.Views.Settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.classmanegerandroid.Navigation.Destinations
import me.saine.android.Classes.CurrentUser

@Composable
fun itemSetting(
    onClick: () -> Unit,
    urlImage: String,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(90.dp)
            .clickable {
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
                        data = urlImage,
                        builder = {
                            scale(Scale.FILL)
                            //placeholder(R.drawable.notification_action_background)
                            transformations(CircleCropTransformation())
                        },
                    ),
                    contentDescription = "Imágen del usuario",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                        .clip(CircleShape),
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
                        text = title,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(4.dp),
                    )
                }
            }
        }
    )
}