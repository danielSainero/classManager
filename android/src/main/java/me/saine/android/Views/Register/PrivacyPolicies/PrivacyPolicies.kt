package me.saine.android.Views.Register.PrivacyPolicies

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.saine.android.R.string.*

@Composable
fun MainPrivacyPolicies(
    navController: NavController,
    mainViewModelPrivacyPolicies: MainViewModelPrivacyPolicies
) {
    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(text = "Políticas de privacidad")
                 },
                 navigationIcon = {
                     IconButton(
                         onClick = {
                             navController.popBackStack()
                         },
                         content = {
                             Icon(
                                 Icons.Filled.ArrowBack,
                                 contentDescription = "",
                                 tint = Color.White
                             )
                         }
                     )
                 }
             )
        },
        content = {
            LazyColumn(
                content = {
                    item {
                        textTitle(mainViewModelPrivacyPolicies.privacyPoliciesTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.privacyPoliciesText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.logDataTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.logDataText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.userInformationTitle)

                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.userInformationText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.securityInformationTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.securityInformationText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.keepInformationTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.keepInformationText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.internationalInformationTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.internationalInformationText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.informationControllingTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.informationControllingText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.cookiesTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.cookiesText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.limitsTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.limitsText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.policityChangesTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.policityChangesText)
                    }
                    item {
                        textTitle(mainViewModelPrivacyPolicies.contactUsTitle)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.contactUsText)
                    }
                    item {
                        Spacer(modifier = Modifier.padding(5.dp))
                        textItem(mainViewModelPrivacyPolicies.contactUsName)
                    }
                    item {
                        textItem(mainViewModelPrivacyPolicies.contactUsMail)
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            )
        }
    )
}

