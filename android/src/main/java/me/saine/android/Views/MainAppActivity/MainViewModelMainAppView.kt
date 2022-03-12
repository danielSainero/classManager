package me.saine.android.Views.MainAppActivity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import me.saine.android.dataClasses.Course

class MainViewModelMainAppView: ViewModel() {

    //Firebase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference



    init {
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        databaseReference = Firebase.database.reference
        storage = Firebase.storage
        storageReference = storage.reference
    }

    var myCourses = mutableListOf<Course>()
    val allCourses = mutableListOf<Course>()


    fun getMyCourses() {
        myCourses.clear()
        allCourses.forEach{
            it.admins.forEach{ admins ->
                if (admins.equals(auth.currentUser?.uid.toString())) {
                    myCourses.add(it)
                }
            }
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