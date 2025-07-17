package com.example.app_orcamind.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.app_orcamind.data.daoUser.SaveUser
import com.example.app_orcamind.model.User
import android.content.Context


@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {

    abstract fun saveUser(): SaveUser

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun instance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "orcaMind.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}