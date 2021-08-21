package com.example.bankapp.bank.data.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bankapp.bank.data.User
import com.example.bankapp.bank.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
@Inject
constructor(private val userRepository: UserRepository) : ViewModel() {

    val response:MutableState<List<User>> = mutableStateOf(listOf())

    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }

    fun delete(user: User) = viewModelScope.launch {
        userRepository.delete(user)
    }

    fun updateAmount(amt: String, id: String) = viewModelScope.launch {
        userRepository.changeAmount(amt, id)
    }

    init {
        getAllUsers()
        Log.d("userViewModel", "${userRepository}")
        //println(userRepository)
    }

    fun getAllUsers() = viewModelScope.launch {
        userRepository.getAllUsers()
            .catch { e ->
                Log.d("main", "Exception: ${e.message} ")
            }.collect {
                response.value = it
            }
    }



}