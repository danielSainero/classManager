package me.saine.android.Views.CreateClass

import androidx.lifecycle.ViewModel
import me.saine.android.Classes.CurrentUser

class MainViewModelCreateClass: ViewModel() {

    fun getNamesOfCurses(): MutableList<String> {
        val namesOfCurses = mutableListOf<String>()
        CurrentUser.myCourses.forEach {
            namesOfCurses.add(it.name)
        }
        return namesOfCurses
    }

}