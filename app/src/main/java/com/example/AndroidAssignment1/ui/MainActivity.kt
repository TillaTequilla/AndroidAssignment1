package com.example.AndroidAssignment1.ui

import android.os.Bundle
import com.example.AndroidAssignment1.util.Constants
import com.example.AndroidAssignment1.databinding.ActivityMainBinding
import com.example.AndroidAssignment1.architecture.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvName.text = intent.getStringExtra(Constants.INTENT_NAME)?: ""
    }
}