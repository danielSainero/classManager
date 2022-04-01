package me.saine.android.Views.Course

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun addNewUser(
    onValueCloseDialog: (Boolean) -> Unit,
    onValueChangeIdOfUser: (String) -> Unit,
    value: String,
    label: String,
    placeholder: String,
    onValueChangeTextSelectedItem: (String) -> Unit,
    onClickSave: () -> Unit
) {
    val suggestion: MutableList<String> = mutableListOf("admin","profesor","padre","alumno")

    Dialog(
        onDismissRequest = {
            onValueCloseDialog(false)
        },
        properties = DialogProperties(

        ),
        content = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .background(Color.White),
                content = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxHeight(0.8f),
                        content = {
                            Spacer(modifier = Modifier.padding(10.dp))
                            TextField(
                                value = value,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        PaddingValues(
                                            start = 20.dp,
                                            end = 20.dp
                                        )
                                    ),
                                label = {
                                    Text(text = label)
                                },
                                placeholder = {
                                    Text(text = placeholder)
                                },
                                onValueChange = {
                                    onValueChangeIdOfUser(it)
                                },
                                singleLine = true,
                            )
                            Spacer(modifier = Modifier.padding(6.dp))
                            bigSelectedDropDownMenu (
                                suggestions = suggestion,
                                onValueChangeTextSelectedItem = onValueChangeTextSelectedItem
                            )
                            Spacer(modifier = Modifier.padding(7.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        PaddingValues(
                                            end = 20.dp
                                        )
                                    ),
                                content = {
                                    TextButton(
                                        onClick = {
                                            onValueCloseDialog(false)
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