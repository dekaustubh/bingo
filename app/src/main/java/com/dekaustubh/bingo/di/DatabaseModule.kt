package com.dekaustubh.bingo.di

import android.app.Application
import androidx.room.Room
import com.dekaustubh.bingo.constants.DI.USER_TOKEN
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.db.dao.RoomDao
import com.dekaustubh.bingo.db.dao.UserDao
import com.dekaustubh.bingo.db.entities.DbUser
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Qualifier
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

    @Provides
    @Singleton
    @Named(USER_TOKEN)
    fun provideLoggedInUserToken(bingoDatabase: BingoDatabase): String {
        return bingoDatabase.userDao().getLoggedInUser().token ?: ""
    }
}