package me.saine.android.Views.Practice

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import me.saine.android.Classes.CurrentUser
import me.saine.android.Classes.CurrentUser.Companion.db
import me.saine.android.dataClasses.Chat
import me.saine.android.dataClasses.Class
import me.saine.android.dataClasses.Message
import me.saine.android.dataClasses.Practice

class MainViewModelPractice: ViewModel() {

    var selectedPractice: Practice = Practice("","", "","","","")
    var chat: Chat = Chat("", arrayListOf())

    fun deletePractice(
        context: Context,
        navController: NavController
    ) {
        db.collection("practices")
            .document(selectedPractice.id)
            .delete()
            .addOnSuccessListener {
                deletePracticeChat(selectedPractice.idOfChat)

                CurrentUser.myClasses.forEach {
                    if(it.id.equals(selectedPractice.idOfClass)) {
                        it.idPractices.remove(selectedPractice.id)
                        deleteIdOfPracticeInTableClass(it.idPractices)
                    }
                }
                CurrentUser.uploadCurrentUser()
                navController.popBackStack()
                Toast.makeText(context,"La clase se ha eliminado correctamente", Toast.LENGTH_SHORT).show()
            }



    }
    fun deleteIdOfPracticeInTableClass(
        newValueOfPractices: MutableList<String>
    ) {
        db.collection("classes")
            .document(selectedPractice.idOfClass)
            .update("idPractices",newValueOfPractices)
    }

    fun deletePracticeChat(idOfChat: String) {
        db.collection("practicesChats")
            .document(idOfChat)
            .delete()
            .addOnSuccessListener {
            }
    }

    fun getPractice(
        idPractice: String
    ) {
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
                /*chat =
                    Chat(
                        id = it.id,
                        idOfPractice = it.get("idOfPractice") as String,
                        conversation = it.get("conversation") as MutableList<Message>
                    )*/
            }
    }
}