package com.seda.shoppingapp.activies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.seda.shoppingapp.R
import com.seda.shoppingapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var doubleBackToExit=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment

       NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.navController)

    }

    override fun onBackPressed() {
        doubleBackToExit()
    }
    fun doubleBackToExit(){
        if(doubleBackToExit){
            super.onBackPressed()
            return
        }
        this.doubleBackToExit=true
        Toast.makeText(this,"back exit",Toast.LENGTH_LONG).show()
     Handler().postDelayed({
         doubleBackToExit=false
     },2000)
    }
}