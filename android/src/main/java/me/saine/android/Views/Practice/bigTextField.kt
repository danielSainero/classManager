package me.saine.android.Views.Practice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun bigTextField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    KeyboardType : KeyboardType
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 40.dp, end = 40.dp)),
        content = {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                },
                placeholder = { Text(text) },
                label = { Text(text = text) },
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth()

            )
        }
    )
}