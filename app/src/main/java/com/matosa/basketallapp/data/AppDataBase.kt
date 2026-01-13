package com.matosa.basketallapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var instance: UserDao? = null

        fun getInstance(context: Context): UserDao {
            return instance ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "basketball-db"
            ).build().userDao().also { instance = it }
        }
    }
}