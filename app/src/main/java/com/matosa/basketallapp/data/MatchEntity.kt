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