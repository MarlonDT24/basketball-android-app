package com.matosa.basketallapp.data

import androidx.room.*

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches WHERE home_team = :teamName OR away_team = :teamName ORDER BY match_date DESC")
    suspend fun getMatchesForTeam(teamName: String): List<MatchEntity>

    @Query("SELECT * FROM player_stats WHERE team_name = :teamName")
    suspend fun getPlayerStatsForTeam(teamName: String): List<PlayerStatsEntity>
}