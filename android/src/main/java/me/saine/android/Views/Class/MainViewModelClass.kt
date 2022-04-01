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
import me.saine.android.data.local.Message
import me.saine.android.data.local.RolUser
import me.saine.android.data.network.AccesToDataBase.Companion.db
import me.saine.android.data.network.ChatImplement.Companion.deleteChatById
import me.saine.android.data.network.PracticeImplement.Companion.deletePracticeById
import me.saine.android.data.network.UsersImplement.Companion.updateUser
import me.saine.android.data.remote.Chat
import me.saine.android.data.remote.Class
import me.saine.android.data.remote.Practice
import me.saine.android.data.remote.appUser

class MainViewModelClass:ViewModel() {

    var selectedClass: Class = Class("","","", arrayListOf(), arrayListOf(),"")
    val selectedPractices = arrayListOf<Practice>()
    lateinit var addNewUser: appUser

    fun getSelectedClass(
        idClass: String,
    ) {
        db.collection("classes")
            .document(idClass)
            .get()
            .addOnSuccessListener {
                val users = it.get("users") as  MutableList<HashMap<String,String>> //Any
                val listOfRolUser: MutableList<RolUser> = mutableListOf()
                users.forEach {
                    listOfRolUser.add(
                        RolUser(
                            id = it.get("id") as String,
                            rol = it.get("rol") as String
                        )
                    )
                }

                selectedClass =
                   Class(
                       id = it.id,
                       idPractices = it.get("idPractices") as MutableList<String>,
                       users = listOfRolUser,
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
            db.collection("practices")
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
                db.collection("classes")
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
                    deletePracticeById(
                        idOfPractice = it.id,
                        onFinished = {}
                    )
                    deleteChatById(
                        idOfChat = it.idOfChat,
                        onFinished = {}
                    )
                }


                CurrentUser.myCourses.forEach{
                    if(it.id.equals(selectedClass.idOfCourse)) {
                        it.classes.remove(selectedClass.id)
                        deleteIdOfCourse(it.classes)
                    }
                }
                CurrentUser.myClasses.remove(selectedClass)
                CurrentUser.currentUser.classes.remove(selectedClass.id)

                selectedClass.users.forEach {
                    deleteIfOfClassByUserId(it.id)
                }

                selectedPractices.clear()

                CurrentUser.uploadCurrentUser()
                navController.popBackStack()
                Toast.makeText(context,"La clase se ha eliminado correctamente", Toast.LENGTH_SHORT).show()
            }
    }

    fun deleteIfOfClassByUserId(
        idOfUser: String
    ) {
        db.collection("users")
            .document(idOfUser)
            .get()
            .addOnSuccessListener {

                if (it.exists()) {

                    val entity = appUser(
                        id = it.get("id") as String,
                        classes = it.get("classes") as MutableList<String>,
                        description = it.get("description") as String,
                        name =  it.get("name") as String,
                        courses = it.get("courses") as MutableList<String>,
                        imgPath = it.get("imgPath") as String,
                        email = it.get("email") as String
                    )
                    entity.classes.remove(selectedClass.id)
                    updateUser(
                        idOfUser = entity.id,
                        user = entity,
                        onFinished = {}
                    )
                }

            }

    }

    fun deleteIdOfCourse(
        newValueOfClasses: MutableList<String>
    ) {
        db.collection("course")
            .document(selectedClass.idOfCourse)
            .update("classes",newValueOfClasses)
    }

    fun addNewUser(
        idOfUser: String,
        context: Context,
        textSelectedItem: String
    ) {
        checkIfUserExist (
            idOfUser = idOfUser,
            context = context,
            onFinishResult = {
                if (it) {
                    if (!checkIfUserIsInscribedInClass(idOfUser)) {
                        selectedClass.users.add(
                            RolUser(
                                id = idOfUser,
                                rol = textSelectedItem
                            )
                        )
                        addNewUser.classes.add(selectedClass.id)
                        db.collection("users")
                            .document("${idOfUser}")
                            .set(addNewUser)
                            .addOnSuccessListener {
                                db.collection("classes")
                                    .document(selectedClass.id)
                                    .set(selectedClass)
                                    .addOnSuccessListener {
                                        CurrentUser.updateDates()
                                        Toast.makeText(context,"El usuario se ha agregado correctamente", Toast.LENGTH_SHORT).show()
                                    }
                            }
                    }
                    else
                        Toast.makeText(context,"El usuario ya participa en esta clase",Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(context,"La id del usuario no existe", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun checkIfUserIsInscribedInClass(
        idOfUser: String
    ):Boolean {
        selectedClass.users.forEach {
            if (it.id.equals(idOfUser))
                return true
        }
        return false
    }

    fun checkIfUserExist(
        idOfUser: String,
        context: Context,
        onFinishResult: (Boolean) -> Unit
    ) {
        db.collection("users")
            .document(idOfUser)
            .get()
            .addOnCompleteListener {

                if (it.result.exists()) {
                    addNewUser = appUser(
                        id = it.result.get("id") as String,
                        classes = it.result.get("classes") as MutableList<String>,
                        description = it.result.get("description") as String,
                        name =  it.result.get("name") as String,
                        courses = it.result.get("courses") as MutableList<String>,
                        imgPath = it.result.get("imgPath") as String,
                        email = it.result.get("email") as String
                    )
                    onFinishResult(true)
                }
                else
                    onFinishResult(false)
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