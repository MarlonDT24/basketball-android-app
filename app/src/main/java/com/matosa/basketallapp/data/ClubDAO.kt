package com.matosa.basketallapp.data

import androidx.room.*

@Dao
interface ClubDao {
    @Query("SELECT * FROM clubs WHERE id = :clubId")
    suspend fun getClubById(clubId: Int): ClubEntity?

    @Query("SELECT * FROM clubs")
    suspend fun getAllClubs(): List<ClubEntity>

    @Insert
    suspend fun insertClub(club: ClubEntity)
}