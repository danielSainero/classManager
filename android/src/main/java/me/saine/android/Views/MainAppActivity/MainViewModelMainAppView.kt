package me.saine.android.Views.MainAppActivity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import me.saine.android.data.remote.Chat
import me.saine.android.data.remote.Course
import me.saine.android.data.local.Message
import me.saine.android.data.network.AccesToDataBase.Companion.auth
import me.saine.android.data.network.AccesToDataBase.Companion.db
import me.saine.android.data.remote.Practice

class MainViewModelMainAppView: ViewModel() {

    //Firebase

    lateinit var chat: Chat
    lateinit var selectedPractice : Practice


    var myCourses = mutableListOf<Course>()
    val allCourses = mutableListOf<Course>()

    var _selectedAppActivityState:MutableState<viewAppActivityState> = mutableStateOf(viewAppActivityState.DEFAULT)
    val selectedAppActivityState: State<viewAppActivityState> = _selectedAppActivityState

    fun updateViewAppActivityState(newValue: viewAppActivityState) {
        _selectedAppActivityState.value = newValue
    }

    fun getMyCourses() {
        myCourses.clear()
        allCourses.forEach{
            it.users.forEach{ admins ->
                if (admins.equals(auth.currentUser?.uid.toString())) {
                    myCourses.add(it)
                }
            }
        }
    }
    fun getChatsOfClass(idPractice: String) {

        db.collection("practices")
            .document(idPractice)
            .get()
            .addOnSuccessListener {
                selectedPractice =
                    Practice(
                        id = it.id,
                        name = it.get("name") as String,
                        teacherAnnotation =  it.get("teacherAnnotation") as String,
                        idOfChat = it.get("idOfChat") as String,
                        description = it.get("description") as String,
                        idOfClass = it.get("idOfClass") as String
                    )
                getChat(selectedPractice.idOfChat)
            }
    }

    fun getChat(
        idChat: String
    ) {

        db.collection("practicesChats")
            .document(idChat)
            .get()
            .addOnSuccessListener {
                chat =
                    Chat(
                        id = it.id,
                        conversation = it.get("conversation") as MutableList<Message>
                    )
            }
    }

    //Searches BD
/*
    fun getCourses(

    ) {
        db.collection("course").get().addOnSuccessListener {
            allCourses.clear()
            for (document in it) {
               /* if (auth.currentUser?.uid.equals(document.id)) {}*/
                allCourses.add(
                    Course(
                        name = document.get("name") as String,
                        classes = document.get("classes") as MutableList<String>,
                        admins = document.get("admins") as MutableList<String>,
                    )
                )
            }
           // getMyCourses()
        }
    }
*/



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