package com.seda.shoppingapp.activies

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.seda.shoppingapp.R
import com.seda.shoppingapp.databinding.FragmentForgetPasswordBinding


class ForgetPasswordFragment : Fragment() {
  private lateinit var _binding: FragmentForgetPasswordBinding
    private val binding get() = _binding
    private lateinit var mProgressDialog: Dialog
    private lateinit var email : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgetPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
   binding.submit.setOnClickListener {
       reset(it)
   }

    }
private fun reset(view: View){
    email = binding.emaill.toString().trim()
    if(email.isEmpty()){
        showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
        hideProgressDialog()
    }else{
        showProgress()
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task->
                hideProgressDialog()
                if(task.isSuccessful){
                    showErrorSnackBar("Emailinizi KOntrol ediniz", "", view)

                } else{
                    showErrorSnackBar("Başırısız işlem", "Tekrar Deneyin", view)
                    hideProgressDialog()
                }
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


    fun showErrorSnackBar(message: String, errorMessage: String, view: View) {
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        sb.setAction(errorMessage) {

        }
        sb.setActionTextColor(Color.RED)
        sb.setTextColor(Color.BLUE)
        sb.setBackgroundTint(Color.WHITE)
        sb.show()
    }
}
