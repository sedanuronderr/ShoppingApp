package com.seda.shoppingapp.activies

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seda.shoppingapp.Firestore.FirestoreClass
import com.seda.shoppingapp.R
import com.seda.shoppingapp.activies.ui.dashboard.DashboardFragment
import com.seda.shoppingapp.databinding.ActivitySettingsBinding
import com.seda.shoppingapp.model.User
import com.seda.shoppingapp.utils.GlideLoader

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySettingsBinding
    lateinit var  fragment : DashboardFragment
    lateinit var userfragmment :UserProfilFragment
    private lateinit var mProgressDialog: Dialog
    private lateinit var auth: FirebaseAuth
  var useridd =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
            useridd = currentUser.uid
        }


        Log.e("djssksl",useridd)
       // getUserDetails()

    }

private fun getUserDetails(){
   showProgress()
    FirestoreClass.registeractivityget(this)

}
    fun userDetailsuccess(user:User){
        hideProgressDialog()
        GlideLoader(this).loadUserPicture(user.image,binding.profile)
        binding.name.text = "${user.firstName} ${user.lastName}"
        binding.gender.text= user.gender
        binding.email2.text= user.email
        binding.mobil.text=user.mobile.toString()
    }
    fun showProgress(){

        mProgressDialog= Dialog(this)
        mProgressDialog.setContentView(R.layout.diaolog_progress)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()

    }

    override fun onResume() {
        super.onResume()

    }
}