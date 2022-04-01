package me.saine.android.Views.Practice

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import me.saine.android.data.local.Message
import me.saine.android.data.network.AccesToDataBase.Companion.db
import me.saine.android.data.remote.Chat
import me.saine.android.data.remote.Practice
import me.saine.android.data.network.AccesToDataBase.Companion.deletePracticeByPractice
import me.saine.android.data.remote.appUser

class MainViewModelPractice: ViewModel() {

    var selectedPractice: Practice = Practice("","", "","","","")
    var chat: Chat = Chat("", arrayListOf())

    fun deletePractice (
        context: Context,
        navController: NavController
    ) {
        deletePracticeByPractice(
            context =context,
            navController = navController,
            selectedPractice = selectedPractice
        )
    }

    /*
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
*/
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

    fun updateChat() {

        db.collection("practicesChats")
            .document(chat.id)
            .update("conversation",chat.conversation)
    }

    fun getChat(
        idChat: String
    ) {
        db.collection("practicesChats")
            .document(idChat)
            .get()
            .addOnSuccessListener { document ->
                val messagesHasMap = document.get("conversation") as  MutableList<HashMap<String,Any>>
                val messages: MutableList<Message> = mutableListOf()
                messagesHasMap.forEach { message ->
                    val myUserHasMap = message.get("sentBy") as HashMap<String,Any>

                    val myUser = appUser(
                        description = myUserHasMap.get("description") as String,
                        name = myUserHasMap.get("name") as String,
                        id = myUserHasMap.get("id") as String,
                        email = myUserHasMap.get("email") as String,
                        imgPath = myUserHasMap.get("imgPath") as String,
                        courses = myUserHasMap.get("courses") as MutableList<String>,
                        classes = myUserHasMap.get("classes") as MutableList<String>
                    )

                    messages.add(
                        Message(
                            sentOn = message.get("sentOn") as String,
                            message = message.get("message") as String,
                            sentBy = myUser
                        )
                    )
                }
                chat =
                    Chat(
                        id = document.id,
                        conversation = messages
                    )
            }
    }
}
