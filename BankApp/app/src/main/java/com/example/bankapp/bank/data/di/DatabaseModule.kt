package com.example.bankapp.bank.data.di

import android.app.Application
import androidx.room.Room
import com.example.bankapp.bank.data.TransactionDao
import com.example.bankapp.bank.data.TransactionDatabase
import com.example.bankapp.bank.data.UserDao
import com.example.bankapp.bank.data.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application):UserDatabase =
        Room.databaseBuilder(
            application,
            UserDatabase::class.java,
            "UserDatabase"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provides(db:UserDatabase):UserDao = db.getDao()


}