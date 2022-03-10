package com.seda.shoppingapp.activies

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seda.shoppingapp.Constants
import com.seda.shoppingapp.Firestore.FirestoreClass
import com.seda.shoppingapp.R
import com.seda.shoppingapp.databinding.FragmentUserProfilBinding
import com.seda.shoppingapp.model.User
import com.seda.shoppingapp.utils.GlideLoader
import java.io.IOException
import java.util.jar.Manifest


 class UserProfilFragment : Fragment(),View.OnClickListener {
     val bundle : UserProfilFragmentArgs by navArgs()
    private lateinit var mProgressDialog: Dialog
    private lateinit var _binding :FragmentUserProfilBinding
       private  val binding  get()=_binding
    private lateinit var auth: FirebaseAuth
  lateinit var userid: String
val completed = 1
private var mselectedImage :Uri?= null
    private var imageUrl:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfilBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       var user:User= User()
        auth = Firebase.auth
         user = bundle.user1
       userid= bundle.user1.id





        binding.first.isEnabled = false
        binding.first.setText(user.firstName)

        binding.last.isEnabled = false
        binding.last.setText(user.lastName)

        binding.email1.isEnabled = false
        binding.email1.setText(user.email)

        binding.userPhoto.setOnClickListener(this)
        binding.submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.user_photo -> {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {

                        val galleryIntent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent, 1)

                    } else {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            2
                        )
                    }
                }
                R.id.submit -> {


                    if (validateUserProfileDetails()) {

                        if (mselectedImage != null)
                            FirestoreClass.uploadImage(this, mselectedImage)
                        else {
                            updateProfildetails()
                        }

                        //key :gender value:male

                    }
                }
            }
        }
    }
    fun updateProfildetails() {
        val userHashMap = mutableMapOf<String,Any>()

        val number = binding.mobilNumber.text.toString().trim()
        val gender = if(binding.male.isChecked){
            Constants.MALE
        }else{
            Constants.FEMALE
        }

        if(imageUrl.isNotEmpty()){
            userHashMap[Constants.IMAGE]=imageUrl

        }
        if(binding.mobilNumber.text!!.isNotEmpty()) {
            userHashMap[Constants.MOBILE]= number.toLong()

        }else {
            // If sign in fails, display a message to the user.

            Toast.makeText(context, "Authentication failed.",
                Toast.LENGTH_SHORT).show()
        }
        userHashMap[Constants.GENDER]= gender
        showProgress()
        userHashMap["profileCompleted"] = completed
        FirestoreClass.updateUser(this,userHashMap,userid)

    }





    fun imageUploadSuccess(imageUrll:String){

        imageUrl =imageUrll
        updateProfildetails()
    }


    fun userProfileUpdate(){
        hideProgressDialog()
        Toast.makeText(requireContext(),"Profile oluşturuldu",Toast.LENGTH_LONG).show()

       val intet =Intent(context,MainActivity2::class.java)
        intet.putExtra("userid",userid)
startActivity(intet)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 2){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,1)
            }
            else{
                Toast.makeText(requireContext(),"sorun",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
if(resultCode == Activity.RESULT_OK){
    if(data != null){
        try{
            mselectedImage = data.data!!
            //   binding.userPhoto.setImageURI(Uri.parse(selected.toString()))

            GlideLoader(requireContext()).loadUserPicture(mselectedImage!!,binding.userPhoto)
        }catch (e:IOException){
            e.printStackTrace()
        }
    }
    else if(resultCode == Activity.RESULT_CANCELED){
        Log.e("request canceled","ımage")
    }
}

    }
    private fun validateUserProfileDetails():Boolean{
        return when{
            TextUtils.isEmpty(binding.mobilNumber.text.toString().trim())->{
                view?.let { FirestoreClass.showErrorSnackBar(" Mobil phone empty","", it) }
             false
            }

         else ->{
             true
         }
        }
    }
    fun showProgress(){

        mProgressDialog= Dialog(requireActivity())
        mProgressDialog.setContentView(R.layout.diaolog_progress)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()

    }

}