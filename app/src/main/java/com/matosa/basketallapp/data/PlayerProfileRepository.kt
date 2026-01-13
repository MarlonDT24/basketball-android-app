package com.matosa.basketallapp.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class PlayerProfileRepository(private val context: Context) {

    private val fileName = "player_profile.txt"

    suspend fun savePlayerProfile(profile: PlayerProfileEntity): Boolean = withContext(Dispatchers.IO) {
        try {
            val content = """
                NOMBRE=${profile.playerName}
                EQUIPO=${profile.teamName}
                POSICION=${profile.position}
                NUMERO_FEDERATIVO=${profile.federationNumber}
                FECHA_NACIMIENTO=${profile.birthDate}
                TELEFONO=${profile.phone}
                EMAIL=${profile.email}
                DIRECCION=${profile.address}
                CONTACTO_EMERGENCIA=${profile.emergencyContact}
                ID=${profile.id}
                CREATED_AT=${profile.createdAt}
                UPDATED_AT=${profile.updatedAt}
            """.trimIndent()

            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(content.toByteArray())
            }
            true
        } catch (e: IOException) {
            false
        }
    }

    suspend fun getPlayerProfile(): PlayerProfileEntity? = withContext(Dispatchers.IO) {
        try {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) return@withContext null

            val content = context.openFileInput(fileName).bufferedReader().use {
                it.readText()
            }

            val lines = content.lines()
            val data = mutableMapOf<String, String>()

            lines.forEach { line ->
                if (line.contains("=")) {
                    val (key, value) = line.split("=", limit = 2)
                    data[key] = value
                }
            }

            PlayerProfileEntity(
                id = data["ID"] ?: "",
                playerName = data["NOMBRE"] ?: "",
                teamName = data["EQUIPO"] ?: "",
                position = data["POSICION"] ?: "",
                federationNumber = data["NUMERO_FEDERATIVO"] ?: "",
                birthDate = data["FECHA_NACIMIENTO"] ?: "",
                phone = data["TELEFONO"] ?: "",
                email = data["EMAIL"] ?: "",
                address = data["DIRECCION"] ?: "",
                emergencyContact = data["CONTACTO_EMERGENCIA"] ?: "",
                createdAt = data["CREATED_AT"]?.toLongOrNull() ?: 0L,
                updatedAt = data["UPDATED_AT"]?.toLongOrNull() ?: 0L
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun hasPlayerProfile(): Boolean = withContext(Dispatchers.IO) {
        File(context.filesDir, fileName).exists()
    }

    suspend fun deletePlayerProfile(): Boolean = withContext(Dispatchers.IO) {
        try {
            context.deleteFile(fileName)
        } catch (e: Exception) {
            false
        }
    }

    suspend fun listPlayerFiles(): Array<String> = withContext(Dispatchers.IO) {
        context.fileList()
    }
}
