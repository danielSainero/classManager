package me.saine.android.Views.Practice

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*


@Composable
fun showDatePicker(
    context: Context,
    textDate: String,
    onValueChangeTextDate: (String) -> Unit,
    label: String,
    placeholder: String
){

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onValueChangeTextDate("$dayOfMonth/$month/$year")
        }, year, month, day
    )

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 40.dp, end = 40.dp)),
        content = {
            OutlinedTextField(
                value = textDate,
                onValueChange = {},
                placeholder = { placeholder },
                label = { Text(text = "${label}") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            datePickerDialog.show()
                        },
                        content = {
                            Icon(
                                imageVector =  Icons.Default.DateRange,
                                contentDescription = "Fecha de entrega",
                            )
                        }
                    )
                }

            )
        }
    )
}