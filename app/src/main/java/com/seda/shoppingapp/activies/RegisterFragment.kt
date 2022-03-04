package com.seda.shoppingapp.activies


import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seda.shoppingapp.Firestore.FirestoreClass
import com.seda.shoppingapp.R
import com.seda.shoppingapp.databinding.FragmentRegisterBinding
import com.seda.shoppingapp.model.User


/**
 *
 */
class RegisterFragment : Fragment() {
    private lateinit var _binding: FragmentRegisterBinding
    private val binding get() = _binding
    private lateinit var ad: String
    private lateinit var lastad: String
    private lateinit var emaill: String
    private lateinit var sifre: String
    private lateinit var confirmm: String
    private lateinit var auth: FirebaseAuth
    private lateinit var mProgressDialog:Dialog

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth


        binding.register.setOnClickListener {
            validation(it)

        }
    }

    fun showErrorSnackBar(message: String, errorMessage: String, view: View) {
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        sb.setAction(errorMessage) {

        }
        sb.setActionTextColor(Color.RED)
        sb.setTextColor(Color.BLACK)
        sb.setBackgroundTint(R.color.teal_200)
        sb.show()
    }

    private fun validation(view: View) {

        ad = binding.firstname.text.toString().trim()
        lastad= binding.lastName.text.toString().trim()
        emaill = binding.email.text.toString().trim()
        sifre = binding.password.text.toString().trim()
        confirmm = binding.password.text.toString().trim()
        if (TextUtils.isEmpty(ad)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            hideProgressDialog()
        } else if (TextUtils.isEmpty(lastad)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            hideProgressDialog()
        } else if (TextUtils.isEmpty(emaill)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            hideProgressDialog()
        } else if (TextUtils.isEmpty(sifre)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            hideProgressDialog()
        } else if (TextUtils.isEmpty(confirmm)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            hideProgressDialog()
        } else if (sifre != confirmm) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            hideProgressDialog()
        } else {

            showProgress()

            auth.createUserWithEmailAndPassword(emaill, sifre)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val db = FirebaseFirestore.getInstance()
                        val firebaseUser : FirebaseUser = task.result!!.user!!

                        hideProgressDialog()
                        // Sign in success, update UI with the signed-in user's information
                        FirestoreClass.showErrorSnackBar("Başarılı Kayıt ", " ", view)
                         //   UserProfileChangeRequest.Builder().setDisplayName(ad).build()
                      //  auth.currentUser!!.updateProfile(update)

           FirebaseAuth.getInstance().signOut()


                    } else {
                        // If sign in fails, display a message to the user.
                        showErrorSnackBar("Bilgileri kontrol ediniz", "Tekrar Deneyin", view)
                        hideProgressDialog()

                    }
                    val firebaseUser : FirebaseUser = task.result!!.user!!
                    val city = User(
                        firebaseUser.uid,
                        ad,
                        lastad,
                        emaill,
                        sifre
                    )
                FirestoreClass.registerUser(this,city)

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