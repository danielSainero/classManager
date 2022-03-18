package me.saine.android.Views.Class

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import me.saine.android.Classes.CurrentUser
import me.saine.android.Views.Class.SearchWidgetState
import me.saine.android.dataClasses.Course
import me.saine.android.dataClasses.Class
import me.saine.android.dataClasses.Practice

class MainViewModelClass:ViewModel() {

    var selectedClass: Class = Class("","","", arrayListOf(), arrayListOf())
    val selectedPractices = arrayListOf<Practice>()
    fun getSelectedClass(
        idCourse: String,
    ) {
        CurrentUser.db.collection("classes")
            .document(idCourse)
            .get()
            .addOnSuccessListener {
                selectedClass =
                   Class(
                       id = it.id,
                       idPractices = it.get("idPractices") as MutableList<String>,
                       admins = it.get("admins") as MutableList<String>,
                       name = it.get("name") as String,
                       description = it.get("description") as String
                   )
                getAllPractices(selectedClass.idPractices)
            }
    }
    fun getAllPractices(
        idPractices: MutableList<String>
    ) {
        selectedPractices.clear()
        idPractices.forEach{ idOfPractice ->
            CurrentUser.db.collection("practices")
                .document(idOfPractice)
                .get()
                .addOnSuccessListener {
                    selectedPractices.add(
                        Practice(
                            description = it.get("description") as String,
                            id = it.id,
                            name = it.get("name") as String,
                            idOfChats = it.get("idOfChats") as MutableList<String>,
                            teacherAnnotation = it.get("teacherAnnotation") as String
                        )
                    )
                }
        }
    }

    //Search Bar
    private val _searchWidgetState: MutableState<SearchWidgetState> = mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun clearSearchBar() {
        _searchTextState.value = ""
        _searchWidgetState.value = SearchWidgetState.CLOSED
    }
}