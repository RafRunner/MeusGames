package com.example.meusgames.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meusgames.databinding.ActivityCreateAccBinding

class CreateAccActivity : AppCompatActivity() {

    private lateinit var bind: ActivityCreateAccBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCreateAccBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnCreateAcc.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}