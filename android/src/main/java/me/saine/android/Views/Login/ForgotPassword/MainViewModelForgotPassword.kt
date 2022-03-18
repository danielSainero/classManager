package me.saine.android.Views.Login.ForgotPassword

import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class MainViewModelForgotPassword: ViewModel() {

    fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$", Pattern.CASE_INSENSITIVE).matcher(text).find()

}