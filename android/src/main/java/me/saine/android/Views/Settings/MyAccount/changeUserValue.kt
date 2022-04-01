package me.saine.android.Views.Settings.MyAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import me.saine.android.Classes.CurrentUser

@Composable
fun changeUserValue(
    onValueChangeChangeName: (Boolean) -> Unit,
    onValueChangeUserName: (String) -> Unit,
    value: String,
    label: String,
    onClickSave: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onValueChangeChangeName(false)
        },
        properties = DialogProperties(

        ),
        content = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(Color.White),
                content = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxHeight(0.8f),
                                content = {
                                   TextField(
                                       value = value,
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .padding(
                                               PaddingValues(
                                                   start = 10.dp,
                                                   end = 10.dp
                                               )
                                           ),
                                       label = {
                                           Text(text = label)
                                       },
                                       onValueChange = {
                                           onValueChangeUserName(it)
                                       }
                                   )
                               }
                            )



                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    TextButton(
                                        onClick = {
                                            onValueChangeChangeName(false)
                                        },
                                        content = {
                                            Text(text = "Cancelar")
                                        }
                                    )
                                    TextButton(
                                        onClick = {
                                            onClickSave()

                                        },
                                        content = {
                                            Text(text = "Save")
                                        }
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

