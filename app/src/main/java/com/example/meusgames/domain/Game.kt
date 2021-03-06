package com.example.meusgames.domain

import com.google.firebase.firestore.QueryDocumentSnapshot
import java.io.Serializable

data class Game(
    val name: String,
    val userId: String,
    val imageId: String,
    val imageUrl: String,
    val releaseDate: String,
    val description: String,
) : Serializable {

    var id = ""

    companion object {
        fun fromDocument(document: QueryDocumentSnapshot): Game {
            val data = document.data

            return Game(
                data["name"] as String,
                data["userId"] as String,
                data["imageId"] as String,
                data["imageUrl"] as String,
                data["releaseDate"] as String,
                data["description"] as String,
            ).apply {
                id = document.id
            }
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "userId" to userId,
            "imageId" to imageId,
            "imageUrl" to imageUrl,
            "releaseDate" to releaseDate,
            "description" to description,
        )
    }
}