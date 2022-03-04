package com.seda.shoppingapp.activies

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.seda.shoppingapp.Firestore.FirestoreClass
import com.seda.shoppingapp.R
import com.seda.shoppingapp.databinding.FragmentUserProfilBinding
import com.seda.shoppingapp.model.User
import com.seda.shoppingapp.utils.GlideLoader
import java.io.IOException
import java.util.jar.Manifest


class UserProfilFragment : Fragment(),View.OnClickListener {
     val bundle : UserProfilFragmentArgs by navArgs()
    private lateinit var _binding :FragmentUserProfilBinding
       private  val binding  get()=_binding
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

         user = bundle.user1
        val keki= bundle.user1.firstName.toString()
        Log.e("cevappppp","${keki}")



        binding.first.isEnabled = false
        binding.first.setText(user.firstName)

        binding.last.isEnabled = false
        binding.last.setText(user.lastName)

        binding.email1.isEnabled = false
        binding.email1.setText(user.email)

        binding.userPhoto.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v !=null){
            when(v.id){

                R.id.user_photo ->{
                    if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){

                            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent,1)

                    }
                    else{
                        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        2)
                    }
                }
            }
        }
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
            val selected = data.data!!
            //   binding.userPhoto.setImageURI(Uri.parse(selected.toString()))

            GlideLoader(requireContext()).loadUserPicture(selected,binding.userPhoto)
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
             false
            }
         else ->{
             true
         }
        }
    }

}