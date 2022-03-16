package me.saine.android.Views.Course

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import me.saine.android.Classes.CurrentUser.Companion.db
import me.saine.android.dataClasses.Course
import me.saine.android.dataClasses.Class

class MainViewModelCourse: ViewModel() {

    var selectedCourse: Course = Course(arrayListOf(), arrayListOf(),"","","")
    val selectedClasses: MutableList<Class> = mutableListOf()

    fun getSelectedCourse(
        idCourse: String,
    ) {
        db.collection("course")
            .document(idCourse)
            .get()
            .addOnSuccessListener {
               selectedCourse =
                    Course(
                        name = it.get("name") as String,
                        classes = it.get("classes") as MutableList<String>,
                        admins = it.get("admins") as MutableList<String>,
                        description = it.get("description") as String,
                        id = it.id
                    )
                getSelectedClasses(selectedCourse.classes)
            }
    }

    fun getSelectedClasses(
        allSelectedClasses: MutableList<String>
    ) {
        selectedClasses.clear()
        allSelectedClasses.forEach{ idOfCourse ->

            db.collection("classes")
                .document(idOfCourse)
                .get()
                .addOnSuccessListener {
                    selectedClasses.add(
                        Class(
                            name = it.get("name") as String,
                            idPractices = it.get("idPractices") as MutableList<String>,
                            admins = it.get("admins") as MutableList<String>,
                            description = it.get("description") as String,
                            id = it.id
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