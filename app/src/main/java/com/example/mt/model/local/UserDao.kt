package com.example.mt.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mt.model.entity.User

@Dao
interface UserDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser (user: User)

    @Delete
    suspend fun deleteUser (user: User)

    @Query("select * from user_table")
    suspend fun getUser ():List<User>


}