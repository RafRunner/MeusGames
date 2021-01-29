package com.example.meusgames.domain

import com.google.firebase.firestore.QueryDocumentSnapshot
import java.io.Serializable

data class Game(
    val name: String,
    val imageId: String,
    val imageUrl: String,
    val releaseDate: String,
) : Serializable {

    var id = ""

    companion object {
        fun fromDocument(document: QueryDocumentSnapshot): Game {
            val data = document.data

            return Game(
                data["name"] as String,
                data["imageId"] as String,
                data["imageUrl"] as String,
                data["releaseDate"] as String,
            ).apply {
                id = document.id
            }
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "imageId" to imageId,
            "imageUrl" to imageUrl,
            "releaseDate" to releaseDate,
        )
    }
}