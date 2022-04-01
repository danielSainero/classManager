package me.saine.android.Views.CreateClass

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.classmanegerandroid.Navigation.Destinations
import me.saine.android.Classes.CurrentUser
import me.saine.android.data.local.RolUser
import me.saine.android.data.network.AccesToDataBase.Companion.auth
import me.saine.android.data.network.AccesToDataBase.Companion.db
import me.saine.android.data.network.ClassesImplement.Companion.createNewClass
import me.saine.android.data.network.CourseImplement
import me.saine.android.data.network.CourseImplement.Companion.updateCourse
import me.saine.android.data.network.UsersImplement
import me.saine.android.data.network.UsersImplement.Companion.updateUser
import me.saine.android.data.remote.Course
import me.saine.android.data.remote.Class

class MainViewModelCreateClass: ViewModel() {

    fun getNamesOfCurses(): MutableList<String> {
        val namesOfCurses = mutableListOf<String>()
        CurrentUser.myCourses.forEach {
            namesOfCurses.add(it.name)
        }
        return namesOfCurses
    }

    fun createClass(
        navController: NavController,
        context: Context,
        textDescription: String,
        textNameOfClass: String,
        itemSelectedCurse: Course
    ) {
       /*val document = db.collection("classes").document()
        val idOfDocument = document.id*/


        val newClass = Class(
            id = "",
            name = textNameOfClass,
            description = textDescription,
            idPractices = mutableListOf<String>(),
            idOfCourse = itemSelectedCurse.id,
            users =  mutableListOf(
                RolUser(
                    id = "${auth.currentUser?.uid}",
                    rol = "admin"
                )
            )
        )

        createNewClass(
            newClass = newClass,
            onFinished = { success, newClass ->
                if (success) {
                    if(!itemSelectedCurse.name.equals("Sin asignar") ) {
                        itemSelectedCurse.classes.add(newClass.id)
                        updateCourse(
                            newCourse = itemSelectedCurse,
                            onFinished = { success, newCourse ->
                                CurrentUser.currentUser.classes.add(newClass.id)
                                updateUser(
                                    idOfUser = auth.currentUser?.uid.toString(),
                                    user = CurrentUser.currentUser,
                                    onFinished = {
                                        if (it) {
                                            CurrentUser.updateDates()
                                            Toast.makeText(context,"El curso ha sido creado correctamente", Toast.LENGTH_SHORT).show()
                                            navController.navigate(Destinations.MainAppView.route)
                                        }
                                    }
                                )
                            }
                        )
                    }
                    else {
                        CurrentUser.currentUser.classes.add(newClass.id)
                        updateUser(
                            idOfUser = auth.currentUser?.uid.toString(),
                            user = CurrentUser.currentUser,
                            onFinished = {
                                if (it) {
                                    CurrentUser.updateDates()
                                    Toast.makeText(context,"El curso ha sido creado correctamente", Toast.LENGTH_SHORT).show()
                                    navController.navigate(Destinations.MainAppView.route)
                                }
                            }
                        )
                    }
                }
            }
        )
/*
        document.set(newClass)
            .addOnSuccessListener {
            if(!itemSelectedCurse.name.equals("Sin asignar") ) {
                itemSelectedCurse.classes.add(idOfDocument)
                db.collection("course")
                    .document(itemSelectedCurse.id)
                    .set(itemSelectedCurse)
            }

            CurrentUser.currentUser.classes.add(idOfDocument)

            db.collection("users")
                .document(auth.currentUser?.uid.toString())
                .set(CurrentUser.currentUser)
                .addOnSuccessListener {
                    CurrentUser.updateDates()
                    Toast.makeText(context,"El curso ha sido creado correctamente", Toast.LENGTH_SHORT).show()
                    navController.navigate(Destinations.MainAppView.route)
                }
        }*/
    }

}