package com.example.meusgames.ui

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

        bind.btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        bind.tvCreateAcocunt.setOnClickListener {
            startActivity(Intent(this, CreateAccActivity::class.java))
        }
    }
}