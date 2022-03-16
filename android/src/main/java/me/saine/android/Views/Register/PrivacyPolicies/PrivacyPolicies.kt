package me.saine.android.Views.Register.PrivacyPolicies

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import me.saine.android.R.string.*

@Composable
fun MainPrivacyPolicies(
    navController: NavController
) {
    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(text = "Políticas de privacidad")
                 }
             )
        },
        content = {
            LazyColumn(
                content = {
                    item {
                        Text(
                            text = "Políticas de privacidad"
                        )
                    }
                    item {
                        Text(
                            text = "Su privacidad es importante para nosotros. Es política de Class Manager respetar su privacidad y cumplir con cualquier ley y " +
                                    "regulación aplicable con respecto a cualquier información persona que podamos recopilar sobre usted.\n" +
                                    "Esta política es efectiva a partir del 1 de noviembre de 2021 y se actualizó por última vez el 1 de noviembre de 2021"
                        )
                    }
                }
            )
        }
    )
}

