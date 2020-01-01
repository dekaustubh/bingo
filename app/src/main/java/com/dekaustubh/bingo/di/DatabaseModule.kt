package com.dekaustubh.bingo.di

import android.app.Application
import androidx.room.Room
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.db.dao.RoomDao
import com.dekaustubh.bingo.db.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideBingoDatabase(application: Application): BingoDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            BingoDatabase::class.java,
            "bingo_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDao(bingoDatabase: BingoDatabase): RoomDao {
        return bingoDatabase.roomDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(bingoDatabase: BingoDatabase): UserDao {
        return bingoDatabase.userDao()
    }
}