package me.saine.android.Classes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import me.saine.android.Views.MainAppActivity.getMyCourses
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
            currentUser.courses.forEach{ idCourse ->

                db.collection("course")
                    .document(idCourse)
                    .get()
                    .addOnSuccessListener {
                        myCourses.add(
                            Course(
                                name = it.get("name") as String,
                                classes = it.get("classes") as MutableList<String>,
                                admins = it.get("admins") as MutableList<String>,
                            )
                        )
                    }
            }
        }

        fun getMyClasses() {
            myClasses.clear()
            currentUser.classes.forEach{ idCourse ->

                db.collection("classes")
                    .document(idCourse)
                    .get()
                    .addOnSuccessListener {
                        myClasses.add(
                            Class(
                                name = it.get("name") as String,
                                idPractices = it.get("idPractices") as MutableList<String>
                            )
                        )
                    }
            }
        }
    }
}