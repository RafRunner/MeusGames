package com.example.meusgames.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.meusgames.databinding.ActivityAddEditGameBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog

class AddEditGameActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_CODE = 1000
    }

    private val storageReference = FirebaseStorage.getInstance().getReference("game_image")

    private lateinit var bind: ActivityAddEditGameBinding
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddEditGameBinding.inflate(layoutInflater)
        setContentView(bind.root)

        alertDialog = SpotsDialog.Builder().setContext(this).build()

        bind.btnAddGameImage.setOnClickListener {
            uploadImage()
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

        if (requestCode == IMAGE_CODE) {
            alertDialog.show()
            val uploadTask = storageReference.putFile(data!!.data!!)
            uploadTask.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show()
                }
                storageReference.downloadUrl

            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result!!.toString()
                    val url = downloadUri.substring(0, downloadUri.indexOf("&token"))

                    Log.i("URL referência ", url)
                    alertDialog.dismiss()
                    Picasso.get()
                        .load(url)
                        .into(bind.btnAddGameImage)
                }
                else {
                    Log.e("Erro ao upload", task.exception.toString())
                    alertDialog.dismiss()
                    Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}