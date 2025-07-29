package com.example.app_orcamind.data.daoUser

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.app_orcamind.data.model.User


@Dao
interface SaveUser {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(vararg user: User)
}