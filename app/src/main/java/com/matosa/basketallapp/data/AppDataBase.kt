package com.matosa.basketallapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        ClubEntity::class,
        CompetitionEntity::class,
        TeamEntity::class,
        MatchEntity::class,
        PlayerStatsEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun clubDao(): ClubDao
    abstract fun matchDao(): MatchDao
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "basketball-db"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}