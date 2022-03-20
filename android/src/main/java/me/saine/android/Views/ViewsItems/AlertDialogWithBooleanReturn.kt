package me.saine.android.Views.ViewsItems

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun confirmAlertDialog(
    title: String,
    subtitle: String,
    onValueChangeGoBack: (Boolean) -> Unit,
    onFinishAlertDialog: (correct:Boolean) -> Unit
) {
    MaterialTheme {

        Column {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = title)
                },
                text = {
                    Text(subtitle)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onFinishAlertDialog(true)
                        },
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            onFinishAlertDialog(false)
                            onValueChangeGoBack(false)
                        },
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

