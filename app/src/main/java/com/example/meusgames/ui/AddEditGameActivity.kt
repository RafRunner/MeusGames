package com.example.meusgames.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meusgames.databinding.ActivityAddEditGameBinding
import com.example.meusgames.domain.Game
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import java.util.*

class AddEditGameActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_CODE = 1000
    }

    private val addEditGameViewModel by viewModels<AddEditGameViewModel>{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AddEditGameViewModel(this@AddEditGameActivity) as T
            }
        }
    }

    private lateinit var bind: ActivityAddEditGameBinding
    private lateinit var alertDialog: AlertDialog

    private var gameId: String = ""
    private var gameImageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddEditGameBinding.inflate(layoutInflater)
        setContentView(bind.root)

        alertDialog = SpotsDialog.Builder().setContext(this).build()

        (intent.getSerializableExtra("game") as Game?)?.let {
            bind.tiGameName.setText(it.name)
            bind.tiGameReleaseDate.setText(it.releaseDate)
            bind.tiGameDescription.setText(it.description)

            Picasso.get()
                .load(it.imageUrl)
                .into(bind.btnAddGameImage)

            gameId = it.id
            gameImageUrl = it.imageUrl
        }

        bind.btnAddGameImage.setOnClickListener {
            uploadImage()
        }

        bind.btnSaveGame.setOnClickListener {
            val gameName = bind.tiGameName.text.toString()
            val gameRelease = bind.tiGameReleaseDate.text.toString()
            val gameDescription = bind.tiGameDescription.text.toString()

            if (gameName == "" || gameRelease == "" || gameDescription == "" || gameImageUrl == "") {
                Toast.makeText(this, "Please, fill all information about the game!", Toast.LENGTH_SHORT).show()
            }

            val game = Game(gameName, "iuhsaydb", gameImageUrl, gameRelease, gameDescription)
            if (gameId != "") {
                game.id = gameId
                addEditGameViewModel.updateGame(game)
                return@setOnClickListener
            }

            addEditGameViewModel.uploadGame(game)
        }
    }

    private fun uploadImage() {
        val intent = Intent().apply {
            type = "image/"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Captura imagem"), IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val storageReference = FirebaseStorage.getInstance().getReference(Date().toString())

        if (requestCode == IMAGE_CODE) {
            alertDialog.show()
            val uploadTask = storageReference.putFile(data!!.data!!)

            uploadTask.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show()
                }
                storageReference.downloadUrl

            }.addOnSuccessListener {
                val url = it.toString().substring(0, it.toString().indexOf("&token"))

                Log.i("URL referência ", url)
                alertDialog.dismiss()
                Picasso.get()
                    .load(url)
                    .into(bind.btnAddGameImage)

                gameImageUrl = url

            }.addOnFailureListener {
                Log.e("Erro ao upload", it.toString())
                alertDialog.dismiss()
                Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show()
            }
        }
    }
}