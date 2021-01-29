package com.example.meusgames.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meusgames.domain.Game

class MainViewModel : ViewModel() {

    val listGames = MutableLiveData<List<Game>>()

    fun getAllGames() {
        listGames.value = listOf(
            Game("God of war", "qwryuqwry", "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2F4.bp.blogspot.com%2F-_G-aCDfhbdo%2FUoKV3qlc0qI%2FAAAAAAAAKfQ%2FOTJpBWgNZqM%2Fs1600%2Fgod%2Bof%2Bwar%2Bii%2Bcover.jpg&f=1&nofb=1", "2006")
        )
    }
}