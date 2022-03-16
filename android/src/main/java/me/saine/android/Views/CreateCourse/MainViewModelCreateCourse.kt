package me.saine.android.Views.CreateCourse

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import me.saine.android.Classes.CurrentUser
import me.saine.android.dataClasses.ListItem

class MainViewModelCreateCourse: ViewModel() {

    var allListItems = mutableListOf<ListItem>()
    val allNameOfClasses =   mutableListOf<String>()

    fun createListItems() {
        allListItems.clear()
        CurrentUser.myClasses.forEach {
            allListItems.add(
                ListItem(
                    title = it.name,
                    isSelected = false,
                    id = it.id
                )
            )
        }
    }
    fun getOfListTitle():MutableList<String> {
        allNameOfClasses.clear()
        allListItems.forEach { label ->
            if (label.isSelected) {
                allNameOfClasses.add(label.title)
            }
        }
        return allNameOfClasses
    }
}