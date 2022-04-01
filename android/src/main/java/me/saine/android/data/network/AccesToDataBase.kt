package me.saine.android.data.network

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import me.saine.android.Classes.CurrentUser
import me.saine.android.data.remote.Practice

class AccesToDataBase {

    companion object {
        var auth: FirebaseAuth = Firebase.auth
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var storage = Firebase.storage
        var storageReference: StorageReference = storage.reference




        fun deletePracticeByPractice (
            context: Context,
            navController: NavController,
            selectedPractice: Practice
        ) {
            db.collection("practices")
                .document(selectedPractice.id)
                .delete()
                .addOnSuccessListener {
                    deletePracticeChatById(selectedPractice.idOfChat)

                    CurrentUser.myClasses.forEach {
                        if(it.id.equals(selectedPractice.idOfClass)) {
                            it.idPractices.remove(selectedPractice.id)
                            deleteIdOfPracticeInTableClass(
                                newValueOfPractices = it.idPractices,
                                selectedPractice = selectedPractice
                            )
                        }
                    }
                    CurrentUser.uploadCurrentUser()
                    navController.popBackStack()
                    Toast.makeText(context,"La clase se ha eliminado correctamente", Toast.LENGTH_SHORT).show()
                }
        }

        fun deleteIdOfPracticeInTableClass (
            newValueOfPractices: MutableList<String>,
            selectedPractice: Practice
        ) {
            db.collection("classes")
                .document(selectedPractice.idOfClass)
                .update("idPractices",newValueOfPractices)
        }

        fun deletePracticeChatById (
            idOfChat: String
        ) {
            db.collection("practicesChats")
                .document(idOfChat)
                .delete()
                .addOnSuccessListener {
                }
        }





    }
}