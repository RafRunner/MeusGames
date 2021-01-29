package com.example.meusgames.ui

import android.content.Context
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
    private val context: Context,
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

        Picasso.with(context)
            .load(game.imageUrl)
            .resize(holder.ivGameImg.width, holder.ivGameImg.height)
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