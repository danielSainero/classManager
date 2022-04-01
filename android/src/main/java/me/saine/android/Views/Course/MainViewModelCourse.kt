package me.saine.android.Views.Course

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import me.saine.android.Classes.CurrentUser
import me.saine.android.Classes.CurrentUser.Companion.currentUser
import me.saine.android.data.remote.Course
import me.saine.android.data.remote.Class
import me.saine.android.data.local.RolUser
import me.saine.android.data.network.AccesToDataBase.Companion.auth
import me.saine.android.data.network.AccesToDataBase.Companion.db
import me.saine.android.data.remote.appUser

class MainViewModelCourse: ViewModel() {

    var selectedCourse: Course = Course(arrayListOf(), arrayListOf(),"","","")
    val selectedClasses: MutableList<Class> = mutableListOf()
    lateinit var addNewUser: appUser

    fun getSelectedCourse(
        idCourse: String,
    ) {
        db.collection("course")
            .document(idCourse)
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

               selectedCourse =
                    Course(
                        name = it.get("name") as String,
                        classes = it.get("classes") as MutableList<String>,
                        users = listOfRolUser,
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
                            users = it.get("admins") as MutableList<RolUser>,
                            description = it.get("description") as String,
                            id = it.id,
                            idOfCourse = it.get("idOfCourse") as String
                        )
                    )
                }
        }
    }

    fun deleteCurse(
        context: Context,
        navController: NavController
    ) {
        db.collection("course")
            .document(selectedCourse.id)
            .delete()
            .addOnSuccessListener {
                selectedClasses.forEach{
                    currentUser.classes.remove(it.id)
                    CurrentUser.myClasses.remove(it)
                    deleteClass(it.id)
                }
                currentUser.courses.remove(selectedCourse.id)

                selectedCourse.users.forEach {
                    deleteIfOfCourseByUserId(it.id)
                }

                CurrentUser.myCourses.remove(selectedCourse)
                selectedCourse = Course(arrayListOf(), arrayListOf(),"","","")
                selectedClasses.clear()

                CurrentUser.uploadCurrentUser()
                navController.popBackStack()
                Toast.makeText(context,"El curso se ha eliminado correctamente",Toast.LENGTH_SHORT).show()
            }
    }
    fun deleteIfOfCourseByUserId(
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
                    entity.classes.remove(selectedCourse.id)
                    uploadUser(entity)
                }
            }
    }

    fun uploadUser(
        appUser: appUser
    ) {
        db.collection("users")
            .document(appUser.id)
            .set(appUser)
    }

    fun deleteClass(idOfClass: String) {
        db.collection("classes")
            .document(idOfClass)
            .delete()
            .addOnSuccessListener {
            }
    }

    fun createNewClass(
        navController: NavController,
        context: Context,
        textDescription: String,
        textNameOfClass: String,
        itemSelectedCurse: Course
    ) {
        val document = db.collection("classes").document()
        val idOfDocument = document.id
        document.set(
            hashMapOf(
                "admins" to  mutableListOf("${auth.currentUser?.uid}"),
                "idPractices" to mutableListOf<String>(),
                "description" to textDescription,
                "name" to textNameOfClass,
                "idOfCourse" to itemSelectedCurse.id
            )
        ).addOnSuccessListener {
            if(!itemSelectedCurse.name.equals("Sin asignar") ) {
                itemSelectedCurse.classes.add(idOfDocument)
                db.collection("course")
                    .document(itemSelectedCurse.id)
                    .set(itemSelectedCurse)
            }

            currentUser.classes.add(idOfDocument)

            db.collection("users")
                .document(auth.currentUser?.uid.toString())
                .set(currentUser)
                .addOnSuccessListener {
                    CurrentUser.updateDates()
                    Toast.makeText(context,"El curso ha sido creado correctamente", Toast.LENGTH_SHORT).show()
                    navController.navigate("${Destinations.Class.route}/${idOfDocument}")
                }
        }
    }
    fun checkIfUserExist (
        idOfUser: String,
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

    fun checkIfUserIsInscribedInCourse(
        idOfUser: String
    ):Boolean {
        selectedCourse.users.forEach {
            if (it.id.equals(idOfUser))
                return true
        }
        return false
    }

    fun addNewUser(
        idOfUser: String,
        context: Context,
        textSelectedItem: String
    ) {
        checkIfUserExist(
            idOfUser = idOfUser,
            onFinishResult = {
                if (it) {
                    if (!checkIfUserIsInscribedInCourse(idOfUser)) {
                        selectedCourse.users.add(
                            RolUser(
                                id = idOfUser,
                                rol = textSelectedItem
                            )
                        )
                        addNewUser.courses.add(selectedCourse.id)
                        db.collection("users")
                            .document("${idOfUser}")
                            .set(addNewUser)
                            .addOnSuccessListener {
                                db.collection("course")
                                    .document(selectedCourse.id)
                                    .set(selectedCourse)
                                    .addOnSuccessListener {
                                        CurrentUser.updateDates()
                                        Toast.makeText(context,"El usuario se ha agregado correctamente", Toast.LENGTH_SHORT).show()
                                    }
                            }
                    }
                    else
                        Toast.makeText(context,"El usuario ya participa en este curso",Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(context,"La id del usuario no existe", Toast.LENGTH_SHORT).show()
            }
        )
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