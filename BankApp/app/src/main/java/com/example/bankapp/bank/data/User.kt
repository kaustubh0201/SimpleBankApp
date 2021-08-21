package com.example.bankapp.bank.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
data class User(
    @PrimaryKey val id: String,
    val name:String,
    val email:String,
    val number:String,
    val currentBalance:String
)
