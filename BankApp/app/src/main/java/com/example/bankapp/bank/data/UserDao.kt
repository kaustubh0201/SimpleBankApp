package com.example.bankapp.bank.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM userTable")
    fun getAllUsers(): Flow<List<User>>

    @Query("UPDATE userTable SET currentBalance = :amt WHERE id = :id")
    fun changeAmount(amt: String, id: String)

    @Delete
    suspend fun delete(user: User)
}