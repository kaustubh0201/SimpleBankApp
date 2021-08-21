package com.example.bankapp.bank.data.ui


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.bank.data.Transaction
import com.example.bankapp.bank.data.TransactionRepository
import com.example.bankapp.bank.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel
@Inject
constructor(private val transactionRepository: TransactionRepository) : ViewModel(){

    val response: MutableState<List<Transaction>> = mutableStateOf(listOf())

    fun insert(transaction: Transaction) = viewModelScope.launch {
        transactionRepository.insert(transaction)
    }

    init {
        getAllTransactions()
        Log.d("transactionViewModel", "${transactionRepository}")
    }

    fun getAllTransactions() = viewModelScope.launch {
        transactionRepository.getAllTransactions()
            .catch { e ->
                Log.d("main", "Exception: ${e.message} ")
            }.collect {
                response.value = it
            }
    }


}