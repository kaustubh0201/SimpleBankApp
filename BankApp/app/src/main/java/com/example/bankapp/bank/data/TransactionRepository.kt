package com.example.bankapp.bank.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepository
@Inject
constructor(private val dao: TransactionDao){
    suspend fun insert(transaction: Transaction) = withContext(Dispatchers.IO){
        dao.insert((transaction))
    }

    fun getAllTransactions(): Flow<List<Transaction>> = dao.getAllTransactions()
}