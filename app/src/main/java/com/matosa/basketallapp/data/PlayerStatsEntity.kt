package com.matosa.basketallapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_stats")
data class PlayerStatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "match_id")
    val matchId: String,
    @ColumnInfo(name = "player_name")
    val playerName: String,
    @ColumnInfo(name = "team_name")
    val teamName: String,
    @ColumnInfo(name = "points")
    val points: Int = 0,
    @ColumnInfo(name = "rebounds")
    val rebounds: Int = 0,
    @ColumnInfo(name = "assists")
    val assists: Int = 0,
    @ColumnInfo(name = "fouls")
    val fouls: Int = 0,
    @ColumnInfo(name = "minutes")
    val minutes: Int = 0,
    @ColumnInfo(name = "games_played")
    val gamesPlayed: Int = 0
)