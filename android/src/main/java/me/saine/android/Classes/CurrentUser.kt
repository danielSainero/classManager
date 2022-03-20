package me.saine.android.Classes

import android.widget.Toast
import com.example.classmanegerandroid.Navigation.Destinations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import me.saine.android.dataClasses.Course
import me.saine.android.dataClasses.appUser
import me.saine.android.dataClasses.Class


class CurrentUser {
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    init {
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        databaseReference = Firebase.database.reference

    }

    companion object {
        var auth: FirebaseAuth = Firebase.auth
        var db:FirebaseFirestore = FirebaseFirestore.getInstance()
        var databaseReference: DatabaseReference = Firebase.database.reference
        var currentUser: appUser = appUser("","","", mutableListOf<String>(), arrayListOf<String>())
        val myCourses: MutableList<Course> = mutableListOf()
        val myClasses: MutableList<Class> = mutableListOf()


        //Varias consultas o una entera¿?
        fun getMyCourses() {
            myCourses.clear()
            currentUser.courses.forEach{ idOfCourse ->

                db.collection("course")
                    .document(idOfCourse)
                    .get()
                    .addOnSuccessListener {
                        myCourses.add(
                            Course(
                                name = it.get("name") as String,
                                classes = it.get("classes") as MutableList<String>,
                                admins = it.get("admins") as MutableList<String>,
                                description = it.get("description") as String,
                                id = it.id
                            )
                        )
                    }
            }
        }

        fun getMyClasses() {
            myClasses.clear()
            currentUser.classes.forEach{ idOfClass ->

                db.collection("classes")
                    .document(idOfClass)
                    .get()
                    .addOnSuccessListener {
                        myClasses.add(
                            Class(
                                id = it.id,
                                name = it.get("name") as String,
                                description = it.get("description") as String,
                                idPractices = it.get("idPractices") as MutableList<String>,
                                admins = it.get("admins") as MutableList<String>,
                                idOfCourse = it.get("idOfCourse") as String
                            )
                        )
                    }
            }
        }

        fun updateDates() {
            getMyClasses()
            getMyCourses()
        }

        fun uploadCurrentUser() {
            db.collection("users")
                .document(auth.currentUser?.uid.toString())
                .set(currentUser).addOnSuccessListener {
                    updateDates()
                }
        }
    }
}