package me.saine.android.Views.Login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun bigPasswordInput(
    value: String,
    onValueChangeValue: (String) -> Unit,
    valueError: Boolean,
    onValueChangeError: (Boolean) -> Unit,
    validateError: (String) -> Boolean,
    ) {
    var hidden by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = value,
        onValueChange = { it2 ->
            onValueChangeValue(it2)
            onValueChangeError(validateError(it2))
        },
        placeholder = { Text(text = "Escribe su contraseña") },
        label = { Text(text = "Contraseña") },
        isError = false,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { hidden = !hidden }) {
                val vector =
                    rememberImagePainter(
                        data =
                        if (hidden) "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Fic_visibility_off.png?alt=media&token=0806f411-e1a8-4fd9-b803-ec53c4a7b4ac"
                        else "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Fic_visibility.png?alt=media&token=93177a8c-40c9-4368-b6bc-6223a3fdb98b"
                    )
                val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                Icon(painter = vector, contentDescription = description)
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 40.dp, end = 40.dp))
            .focusRequester(focusRequester)
    )
}