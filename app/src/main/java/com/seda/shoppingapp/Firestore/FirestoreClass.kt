package com.seda.shoppingapp.Firestore

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.seda.shoppingapp.activies.LoginFragment
import com.seda.shoppingapp.activies.RegisterFragment
import com.seda.shoppingapp.model.User

class FirestoreClass {

companion object {

    fun registerUser(activity: RegisterFragment, userInfo:User) {
       val db = FirebaseFirestore.getInstance()
       // val db = Firebase.firestore
        db.collection("users")
            .document(userInfo.id.toString())
            .set(userInfo)
            .addOnSuccessListener {e->

               Log.e("kelime","${userInfo}")
            }
            .addOnFailureListener { e ->

                Log.e(
                    activity.javaClass.simpleName, "Error", e
                )
            }
    }


    fun showErrorSnackBar(message: String, errorMessage: String, view: View) {
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        sb.setAction(errorMessage) {

        }
        sb.setActionTextColor(Color.RED)
        sb.setTextColor(Color.BLUE)
        sb.setBackgroundTint(Color.GREEN)
        sb.show()
    }


    fun getcurrentId():String{
        var currentId =""
        val currentUser= FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
            currentId= currentUser.uid
        }
        return currentId
    }

    fun registerget(activity:Fragment,view: View){
        val db = FirebaseFirestore.getInstance()
       db.collection("users").document(getcurrentId())
           .get().addOnSuccessListener { documentSnapshot ->
            val city = documentSnapshot.toObject(User::class.java)
   val per = activity.activity?.getSharedPreferences("kisiselbilgiler", Context.MODE_PRIVATE)
            val editor =per?.edit()
            editor?.putString("username","${city?.firstName} ")
            editor?.putString("lastname","${city?.lastName}")
            editor?.putString("email","${city?.email}")
            editor?.apply()



               when(activity){
                   is LoginFragment->{
                       if (city != null) {
                           activity.userLoggedInSuccess(city,view)
                       }
                   }
               }
        }

    }


}


}