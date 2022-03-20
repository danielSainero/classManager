package me.saine.android.Views.Class

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import com.google.android.gms.tasks.Task
import me.saine.android.Classes.CurrentUser
import me.saine.android.Classes.CurrentUser.Companion.db
import me.saine.android.dataClasses.*

class MainViewModelClass:ViewModel() {

    var selectedClass: Class = Class("","","", arrayListOf(), arrayListOf(),"")
    val selectedPractices = arrayListOf<Practice>()

    fun getSelectedClass(
        idClass: String,
    ) {
        db.collection("classes")
            .document(idClass)
            .get()
            .addOnSuccessListener {
                selectedClass =
                   Class(
                       id = it.id,
                       idPractices = it.get("idPractices") as MutableList<String>,
                       admins = it.get("admins") as MutableList<String>,
                       name = it.get("name") as String,
                       description = it.get("description") as String,
                       idOfCourse = it.get("idOfCourse") as String
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
                            idOfChat = it.get("idOfChat") as String,
                            teacherAnnotation = it.get("teacherAnnotation") as String,
                            idOfClass = it.get("idOfClass") as String
                        )
                    )
                }
        }
    }

    fun createNewPractice(
        name:String,
        navController: NavController
    ) {
        createPracticeChat { idOfChat ->
            val document = db.collection("practices").document()
            val idOfDocument = document.id
            document.set(
                hashMapOf(
                    "deliveryDate" to "",
                    "description" to "",
                    "idOfChat" to idOfChat,
                    "name" to name,
                    "teacherAnnotation" to "",
                    "idOfClass" to selectedClass.id
                )
            ).addOnSuccessListener {
                selectedClass.idPractices.add(idOfDocument)
                CurrentUser.db.collection("classes")
                    .document(selectedClass.id)
                    .set(selectedClass).addOnSuccessListener {
                        CurrentUser.updateDates()
                        navController.navigate("${Destinations.Practice.route}/${idOfDocument}")
                    }
            }
        }

    }

    fun createPracticeChat(onValueFinish: (String) -> Task<Void>) {
        val document = db.collection("practicesChats").document()
        val idOfDocument = document.id

        val newChat = Chat("", arrayListOf(Message("", CurrentUser.currentUser,"")))
        document.set(
                hashMapOf(
                    "conversation" to  mutableListOf<Message>(),
                )
            ).addOnSuccessListener{
                onValueFinish(idOfDocument)
            }
    }

    fun deleteClass(
        context: Context,
        navController: NavController
    ) {
        db.collection("classes")
            .document(selectedClass.id)
            .delete()
            .addOnSuccessListener {
                selectedPractices.forEach{

                    deletePractice(it.id)
                    deletePracticeChat(it.idOfChat)
                }


                CurrentUser.myCourses.forEach{
                    if(it.id.equals(selectedClass.idOfCourse)) {
                        it.classes.remove(selectedClass.id)
                        deleteIdOfCourse(it.classes)
                    }
                }
                CurrentUser.myClasses.remove(selectedClass)
                CurrentUser.currentUser.classes.remove(selectedClass.id)

                selectedClass = Class("","","", arrayListOf(), arrayListOf(),"")
                selectedPractices.clear()

                CurrentUser.uploadCurrentUser()
                navController.popBackStack()
                Toast.makeText(context,"La clase se ha eliminado correctamente", Toast.LENGTH_SHORT).show()
            }
    }

    fun deleteIdOfCourse(
        newValueOfClasses: MutableList<String>
    ) {
        db.collection("course")
            .document(selectedClass.idOfCourse)
            .update("classes",newValueOfClasses)
    }

    fun deletePractice(idOfPractice: String) {
        db.collection("practices")
            .document(idOfPractice)
            .delete()
    }
    fun deletePracticeChat(idOfChat: String) {
        db.collection("practicesChats")
            .document(idOfChat)
            .delete()
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