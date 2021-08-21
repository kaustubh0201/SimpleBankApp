package com.example.bankapp.bank.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 2, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase(){

    abstract fun getDao():TransactionDao
}