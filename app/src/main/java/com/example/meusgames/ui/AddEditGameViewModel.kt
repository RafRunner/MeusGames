package com.example.meusgames.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.meusgames.domain.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AddEditGameViewModel(val context: Context) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun uploadGame(game: Game) {
        db.collection("game")
            .add(game.toMap())
            .addOnSuccessListener {
                Toast.makeText(context, "Game uploaded!", Toast.LENGTH_SHORT).show()
                Log.d(MainActivity.TAG, "Game adicionado com o id: ${it.id}")
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error uploading game", Toast.LENGTH_SHORT).show()
                Log.e(MainActivity.TAG, "Erro ao criar game", it)
            }
    }

    fun updateGame(game: Game) {
        db.collection("game")
            .document(game.id)
            .update(game.toMap())
            .addOnSuccessListener {
                Toast.makeText(context, "Game saved!", Toast.LENGTH_SHORT).show()
                Log.d(MainActivity.TAG, "Game de id ${game.id} atualizado")
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error saving game", Toast.LENGTH_SHORT).show()
                Log.e(MainActivity.TAG, "Erro ao criar game", it)
            }
    }

    fun deleteGame(game: Game) {
        db.collection("game")
            .document(game.id)
            .delete()
            .addOnSuccessListener {
                val storageReference = FirebaseStorage.getInstance().getReference(game.imageId)
                storageReference.delete()
                Toast.makeText(context, "Game deleted!", Toast.LENGTH_SHORT).show()
                (context as Activity).finish()
                Log.d(MainActivity.TAG, "Game de id ${game.id} deletado")
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error deleting game!", Toast.LENGTH_SHORT).show()
                Log.d(MainActivity.TAG, "Erro ao deletar game de id ${game.id}")
            }
    }
}