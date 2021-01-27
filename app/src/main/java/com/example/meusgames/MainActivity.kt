package com.example.meusgames

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.meusgames.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var bind: ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        db = FirebaseFirestore.getInstance()
        readAllDocuments()
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
}