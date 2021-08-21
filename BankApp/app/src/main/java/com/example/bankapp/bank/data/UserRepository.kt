package com.example.bankapp.bank.data


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository
@Inject
constructor(private val dao: UserDao){

    suspend fun insert(user: User) = withContext(Dispatchers.IO){
        dao.insert((user))
    }

    fun getAllUsers(): Flow<List<User>> = dao.getAllUsers()

    suspend fun delete(user: User) = withContext(Dispatchers.IO){
        dao.delete((user))
    }

    suspend fun changeAmount(amt: String, id: String) = withContext(Dispatchers.IO){
        dao.changeAmount(amt, id)
    }

}