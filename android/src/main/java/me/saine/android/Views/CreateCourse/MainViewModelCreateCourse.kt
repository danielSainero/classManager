package me.saine.android.Views.CreateCourse

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import me.saine.android.Classes.CurrentUser
import me.saine.android.data.remote.Course
import me.saine.android.data.remote.ListItem
import me.saine.android.data.local.RolUser
import me.saine.android.data.network.AccesToDataBase.Companion.auth
import me.saine.android.data.network.AccesToDataBase.Companion.db
import me.saine.android.data.network.CourseImplement
import me.saine.android.data.network.CourseImplement.Companion.createNewCourse
import me.saine.android.data.network.UsersImplement
import me.saine.android.data.network.UsersImplement.Companion.updateUser

class MainViewModelCreateCourse: ViewModel() {

    var allListItems = mutableListOf<ListItem>()
    val allNameOfAvailablesClasses =   mutableListOf<String>()

    fun createCourse(
        textNameOfCourse: String,
        textOfDescription: String,
        navController: NavController,
        context: Context
    ) {
        var mySelectedClasses = mutableListOf<String>()
        allListItems.forEach {
            if (it.isSelected)  mySelectedClasses.add(it.id)
        }

        val newCourse = Course(
            name = textNameOfCourse,
            description = textOfDescription,
            id = "",
            classes = mySelectedClasses,
            users = mutableListOf<RolUser>(
                RolUser(
                    id = "${auth.currentUser?.uid}",
                    rol = "admin"
                )
            )
        )

        createNewCourse(
            newCourse = newCourse,
            onFinished = { success , newCourse ->
                if (success) {
                    CurrentUser.currentUser.courses.add(newCourse.id)
                    updateUser(
                        idOfUser = auth.currentUser?.uid.toString(),
                        user = CurrentUser.currentUser,
                        onFinished = { success ->
                            if (success) {
                                CurrentUser.updateDates()
                                Toast.makeText(context,"El curso ha sido creado correctamente", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        }
                    )
                    mySelectedClasses.forEach{
                        db.collection("classes")
                            .document(it)
                            .update(
                                "idOfCourse" , newCourse.id
                            )
                    }

                }
            }
        )


    }

    fun createListItems() {
        allListItems.clear()
        CurrentUser.myClasses.forEach {
            if (it.idOfCourse.equals("Sin asignar")) {
                allListItems.add(
                    ListItem(
                        title = it.name,
                        isSelected = false,
                        id = it.id
                    )
                )
            }
        }
    }
    fun getOfListTitle():MutableList<String> {
        allNameOfAvailablesClasses.clear()
        allListItems.forEach { label ->
            if (label.isSelected) {
                allNameOfAvailablesClasses.add(label.title)
            }
        }
        return allNameOfAvailablesClasses
    }
}