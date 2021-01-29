package com.example.meusgames.ui

import android.os.Bundle
import android.util.Log
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
    private val mainAdapter = MainAdapter(this, ::onGameClicked)
    private val db = FirebaseFirestore.getInstance()

    private var lastFilterText = ""

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        readAllDocuments()

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

                    return false
                }
            })
        }

        mainViewModel.listGames.observe(this) {
            mainAdapter.updateGameList(it)
        }

        mainViewModel.getAllGames()
    }

    private fun readAllDocuments() {
        db.collection("teste")
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty) {
                        val objetoTeste = mapOf("nome" to "Fulano",
                                "sobrenome" to "da Silva")
                        uploadDocument(objetoTeste)
                        return@addOnSuccessListener
                    }

                    it.forEach { document ->
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, "Erro ao ler documentos", it)
                }
    }

    private fun uploadDocument(document: Any) {
        db.collection("teste")
                .add(document)
                .addOnSuccessListener {
                    Log.d(TAG, "Objeto adicionado com o id: ${it.id}")
                }
                .addOnFailureListener {
                    Log.w(TAG, "Erro ao criar objeto", it)
                }
    }

    private fun filterGames(filterText: String) {
        if (lastFilterText == filterText) return

        lastFilterText = filterText
        Log.d(TAG, filterText)
    }

    private fun onGameClicked(game: Game) {
        Log.d(TAG, game.toString())
    }
}