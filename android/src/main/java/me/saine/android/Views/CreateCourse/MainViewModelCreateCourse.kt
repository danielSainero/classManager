package me.saine.android.Views.CreateCourse

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import me.saine.android.Classes.CurrentUser
import me.saine.android.dataClasses.ListItem

class MainViewModelCreateCourse: ViewModel() {

    var allListItems = mutableListOf<ListItem>()
    val allNameOfAvailablesClasses =   mutableListOf<String>()

    fun createListItems() {
        allListItems.clear()
        CurrentUser.myClasses.forEach {
            if (it.idOfCourse.equals("Sin asignar")) {
                allListItems.add(
                    ListItem(
                        title = it.name,
                        isSelected = false,
                        id = it.id
                    )
                )
            }
        }
    }
    fun getOfListTitle():MutableList<String> {
        allNameOfAvailablesClasses.clear()
        allListItems.forEach { label ->
            if (label.isSelected) {
                allNameOfAvailablesClasses.add(label.title)
            }
        }
        return allNameOfAvailablesClasses
    }
}