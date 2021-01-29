package com.example.meusgames.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.meusgames.domain.Game
import com.google.firebase.firestore.FirebaseFirestore

class AddEditGameViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun uploadGame(game: Game) {
        db.collection("game")
            .add(game.toMap())
            .addOnSuccessListener {

                Log.d(MainActivity.TAG, "Objeto adicionado com o id: ${it.id}")
            }
            .addOnFailureListener {
                Log.w(MainActivity.TAG, "Erro ao criar objeto", it)
            }
    }
}