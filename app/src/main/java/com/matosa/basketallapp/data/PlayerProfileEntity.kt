package com.matosa.basketallapp.data

data class PlayerProfileEntity(
    val id: String = "",
    val playerName: String = "",
    val teamName: String = "",
    val position: String = "",
    val federationNumber: String = "",
    val birthDate: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val emergencyContact: String = "",
    val profileImagePath: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)