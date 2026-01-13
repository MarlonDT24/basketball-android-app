package com.matosa.basketallapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competitions")
data class CompetitionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "competition_name")
    val competitionName: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "season")
    val season: String = "2024-2025"
)

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "team_name")
    val teamName: String,
    @ColumnInfo(name = "competition_id")
    val competitionId: Int,
    @ColumnInfo(name = "games_played")
    val gamesPlayed: Int = 0,
    @ColumnInfo(name = "wins")
    val wins: Int = 0,
    @ColumnInfo(name = "losses")
    val losses: Int = 0,
    @ColumnInfo(name = "points_for")
    val pointsFor: Int = 0,
    @ColumnInfo(name = "points_against")
    val pointsAgainst: Int = 0,
    @ColumnInfo(name = "position")
    val position: Int = 0
)