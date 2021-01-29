package com.example.meusgames.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meusgames.R
import com.example.meusgames.domain.Game
import com.squareup.picasso.Picasso

class MainAdapter(
    private val onGameClicked: (g: Game) -> Unit,
) : RecyclerView.Adapter<MainAdapter.GameViewHolder>() {

    private var listGames: List<Game> = listOf()

    inner class GameViewHolder(gameView: View) : RecyclerView.ViewHolder(gameView) {
        val ivGameImg: ImageView = gameView.findViewById(R.id.ivGameImag)
        val tvGameName: TextView = gameView.findViewById(R.id.tvGameName)
        val tvGameRelease: TextView = gameView.findViewById(R.id.tvGameReleaseDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val gameView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_game,
            parent,
            false
        )

        return GameViewHolder(gameView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = listGames[position]

        Picasso.get()
            .load(game.imageUrl)
            .resize(200, 200)
            .centerCrop()
            .into(holder.ivGameImg)

        holder.tvGameName.text = game.name
        holder.tvGameRelease.text = game.releaseDate

        holder.itemView.setOnClickListener {
            onGameClicked(game)
        }
    }

    override fun getItemCount(): Int = listGames.size

    fun updateGameList(games: List<Game>) {
        listGames = games
        notifyDataSetChanged()
    }
}