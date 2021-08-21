package com.example.bankapp.bank.data

import android.icu.util.CurrencyAmount
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactionTable")
data class Transaction(
    val toID: String,
    val fromID: String,
    val date: String,
    val amount: String
) {
    @PrimaryKey(autoGenerate = true)
    var txnID: Int? = null
}
