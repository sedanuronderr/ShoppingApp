package com.seda.shoppingapp.Firestore

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.seda.shoppingapp.R
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

               Log.e("kelime","sededese")
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

    fun registerget(activity:Fragment){
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(getcurrentId())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val city = documentSnapshot.toObject(User::class.java)
   val per= activity.activity?.getSharedPreferences("kisiselbilgiler", Context.MODE_PRIVATE)
            val editor =per?.edit()
            editor?.putString("username","${city?.firstName} ")
            editor?.putString("lastname","${city?.lastName}")
            editor?.putString("email","${city?.email}")
            editor?.apply()
            Log.e("cevap","${city?.lastName}")
        }

    }
    fun userLoggedInSuccess(user:User,view: View){

        if(user.profileCompleted == 0){
            Navigation.findNavController(view).navigate(R.id.userProfilFragment)


        }else{
            Navigation.findNavController(view).navigate(R.id.baseFragment)
        }
    }

}


}