package com.example.bankapp.bank.data.di

import android.app.Application
import androidx.room.Room
import com.example.bankapp.bank.data.TransactionDao
import com.example.bankapp.bank.data.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseTransactionModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): TransactionDatabase =
        Room.databaseBuilder(
            application,
            TransactionDatabase::class.java,
            "TransactionDatabase"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provides(db: TransactionDatabase): TransactionDao = db.getDao()
}