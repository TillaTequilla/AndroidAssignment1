package com.example.AndroidAssignment1.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.example.AndroidAssignment1.R
import com.example.AndroidAssignment1.util.Constants
import com.example.AndroidAssignment1.databinding.ActivityAuthBinding
import com.example.AndroidAssignment1.util.NameParser
import com.example.AndroidAssignment1.architecture.BaseActivity

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    // could be lazy init here
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        getPreferencesData()
        listenerInitialization()
    }

    private fun listenerInitialization() {
        with(binding) {
            // do after text changed
            etPassword.doOnTextChanged { text, start, before, count ->
                if (text!!.length < 5) {
                    tilPassword.error = getString(R.string.error_password_few_symbols)
                } else if (!text.contains("[0-9]".toRegex())) {
                    tilPassword.error = getString(R.string.error_password_number)
                } else tilPassword.error = null
            }

            tietEmail.doOnTextChanged { text, start, before, count ->
                if (!text!!.contains(".+\\..+@+[A-Za-z]+\\.[A-Za-z]+".toRegex()) // move regex to external file or function
                    || text.contains(" ") // you can probably combine space validation in the same regexp
                ) {
                    tilEmail.error = getString(R.string.error_email_valid_email)
                } else tilEmail.error = null
            }

            // at least btn, though I dislike either
            bRegister.setOnClickListener {
                if (cbRememberMe.isChecked) {
                    rememberInformation()

                } else sharedPreferences.edit().clear().apply()
                if (checkForInput()) {
                    val name: String = getName()
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    intent.putExtra(Constants.INTENT_NAME, name)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                }
            }
        }
    }

    private fun checkForInput(): Boolean {
        with(binding) {
            return tilEmail.error == null && tilPassword.error == null
                    && tietEmail.text!!.isNotEmpty() && etPassword.text!!.isNotEmpty()
        }
    }

    private fun getPreferencesData() {
        if (sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_REMEMBER, false)) {
            // scope function would be nice here
            binding.tietEmail.setText(
                sharedPreferences.getString(
                    Constants.SHAREDPREFERENCES_EMAIL,
                    null
                )
            )
            binding.etPassword.setText(
                sharedPreferences.getString(
                    Constants.SHAREDPREFERENCES_PASSWORD,
                    null
                )
            )
            binding.cbRememberMe.isChecked = true
        }
    }

    private fun rememberInformation() {
        // use Preferences helper
        val checked = binding.cbRememberMe.isChecked
        val editor = sharedPreferences.edit()
        editor.putString(Constants.SHAREDPREFERENCES_EMAIL, binding.tietEmail.text.toString())
        editor.putString(Constants.SHAREDPREFERENCES_PASSWORD, binding.etPassword.text.toString())
        editor.putBoolean(Constants.SHARED_PREFERENCES_REMEMBER, checked)
        editor.apply()
    }

    private fun getName(): String {
        return NameParser.getName(binding.tietEmail.text.toString())
    }
}
