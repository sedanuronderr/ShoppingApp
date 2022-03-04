package com.seda.shoppingapp.activies

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seda.shoppingapp.R
import com.seda.shoppingapp.databinding.FragmentBaseBinding


class BaseFragment : Fragment() {
 private lateinit var _binding:FragmentBaseBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        _binding = FragmentBaseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val per= activity?.getSharedPreferences("kisiselbilgiler", Context.MODE_PRIVATE)
  val username = per?.getString("username","")
        Log.e("cevap","${username}")
        binding.user.text = username
    }
}