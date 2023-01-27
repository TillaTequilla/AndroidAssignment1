package com.example.AndroidAssignment1

import android.os.Bundle
import com.example.AndroidAssignment1.constance.Constance
import com.example.AndroidAssignment1.databinding.ActivityMainBinding
import com.example.androidAssignment2.architecture.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name: String? = intent.getStringExtra(Constance.INTENT_NAME)
        binding.tvName.text = name
    }
}