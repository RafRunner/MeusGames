package com.example.meusgames.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meusgames.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var bind: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val prefs = getSharedPreferences("login", Context.MODE_PRIVATE)
        val rememberMe = prefs.getBoolean("remember_me", true)

        bind.checkRemember.isChecked = rememberMe

        bind.checkRemember.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("remember_me", isChecked).apply()
        }

        bind.btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        bind.tvCreateAcocunt.setOnClickListener {
            startActivity(Intent(this, CreateAccActivity::class.java))
        }
    }
}