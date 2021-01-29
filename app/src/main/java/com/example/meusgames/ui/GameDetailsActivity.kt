package com.example.meusgames.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meusgames.R
import com.example.meusgames.databinding.ActivityGameDetailsBinding
import com.example.meusgames.domain.Game
import com.squareup.picasso.Picasso

class GameDetailsActivity : AppCompatActivity() {

    private lateinit var bind: ActivityGameDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnBack.setOnClickListener {
            onBackPressed()
        }

        val game = intent.getSerializableExtra("game") as? Game ?: return

        bind.tvGameNameHighlight.text = game.name
        bind.tvGameName.text = game.name
        bind.tvGameReleaseDate.text = getString(R.string.released).format(game.releaseDate)
        bind.tvGameDescription.text = game.description

        Picasso.get()
            .load(game.imageUrl)
            .resize(500, 500)
            .centerCrop()
            .into(bind.ivGameBanner)

        bind.btnEditGame.setOnClickListener {
            startActivity(Intent(this, AddEditGameActivity::class.java).apply {
                putExtra("game", game)
            })
            finish()
        }
    }
}