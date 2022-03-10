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

class SettingsActivity : AppCompatActivity(),View.OnClickListener {

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

        getUserDetails()
        binding.logout.setOnClickListener(this)
        binding.edit.setOnClickListener(this)
        setupActionBar()
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

    override fun onClick(v: View?) {
if(v != null){
    when(v.id){
        R.id.edit->{
            val intent=Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }

        R.id.logout->{
            FirebaseAuth.getInstance().signOut()
            val intent=Intent(this,MainActivity::class.java)
            intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}

    }
    private fun setupActionBar(){
        setSupportActionBar(binding.toolbar7)

        val actionBar =supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back)
        }
        binding.toolbar7.setNavigationOnClickListener{onBackPressed()}
    }


}