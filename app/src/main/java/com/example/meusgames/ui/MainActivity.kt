package com.example.meusgames.ui

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meusgames.databinding.ActivityMainBinding
import com.example.meusgames.domain.Game
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val mainViewModel by viewModels<MainViewModel>()
    private val mainAdapter = MainAdapter(::onGameClicked)

    private var lastFilterText = ""

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.recyclerViewGames.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(false)
        }

        bind.searchViewGames.apply {
            isIconifiedByDefault = false

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query == null) return false

                    filterGames(query)

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null) return false

                    if (newText.length >= 3) {
                        filterGames(newText)
                    }
                    else if (newText.isEmpty()) {
                        mainViewModel.clearFilter()
                    }

                    return false
                }
            })
        }

        mainViewModel.listGames.observe(this) {
            mainAdapter.updateGameList(it)
        }

        bind.btnAddGame.setOnClickListener {
            startActivity(Intent(this, AddEditGameActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getAllGames()
        bind.searchViewGames.setQuery("", false)
        bind.searchViewGames.clearFocus()
    }

    private fun filterGames(filterText: String) {
        if (lastFilterText == filterText) return

        lastFilterText = filterText
        mainViewModel.filterGames(filterText)
    }

    private fun onGameClicked(game: Game) {
        startActivity(Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game", game)
        })
    }
}