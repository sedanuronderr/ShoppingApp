package com.seda.shoppingapp.activies

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.seda.shoppingapp.Firestore.FirestoreClass
import com.seda.shoppingapp.R
import com.seda.shoppingapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var _binding:FragmentLoginBinding
    private val binding get() = _binding
    private lateinit var auth: FirebaseAuth

    private lateinit var email : String
    private lateinit var password : String
    private lateinit var username:String
    private lateinit var lastname:String
    private lateinit var emailll :String
    private lateinit var mProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
  binding.tvRegister.setOnClickListener {
Navigation.findNavController(it).navigate(R.id.registerFragment)

  }

        if(auth.currentUser != null){

            val intent = Intent(context,MainActivity2::class.java)
            startActivity(intent)
            activity?.finish()
        }


        binding.btnLogin.setOnClickListener {
            login(it)

        }
        binding.sifreunuttum.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.forgetPasswordFragment)
        }






    }


    private fun login(view: View){
        email =binding.email.text.toString().trim()
        password= binding.passwordd.text.toString().trim()



        if (TextUtils.isEmpty(email) ) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            hideProgressDialog()
        }
        else if (TextUtils.isEmpty(password) ) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            hideProgressDialog()
        }
        else{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirestoreClass.registerget(this,view)
                        // Sign in success, update UI with the signed-in user's information


                       auth.signOut()
                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }



    fun userLoggedInSuccess(user: com.seda.shoppingapp.model.User,view: View){

        if(user.profileCompleted == 0){
            val gecis =LoginFragmentDirections.actionLoginFragmentToUserProfilFragment(user)
            Navigation.findNavController(view).navigate(gecis)



        }else{

            val intent = Intent(context,MainActivity2 ::class.java)
            startActivity(intent)
            activity?.finish()
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


    fun showErrorSnackBar(message: String, errorMessage: String, view: View) {
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        sb.setAction(errorMessage) {

        }
        sb.setActionTextColor(Color.RED)
        sb.setTextColor(Color.BLACK)
        sb.setBackgroundTint(Color.WHITE)
        sb.show()
    }


}