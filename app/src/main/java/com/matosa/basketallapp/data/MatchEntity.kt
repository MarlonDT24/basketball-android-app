package com.matosa.basketallapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "home_team")
    val homeTeam: String,
    @ColumnInfo(name = "away_team")
    val awayTeam: String,
    @ColumnInfo(name = "home_score")
    val homeScore: Int,
    @ColumnInfo(name = "away_score")
    val awayScore: Int,
    @ColumnInfo(name = "match_date")
    val matchDate: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "venue")
    val venue: String? = null,
    @ColumnInfo(name = "quarter")
    val quarter: String = "4T",
    @ColumnInfo(name = "time_remaining")
    val timeRemaining: String = "12:30"
)

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
    val minutes: Int = 0
)