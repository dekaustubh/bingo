package com.dekaustubh.bingo.di

import android.app.Application
import androidx.room.Room
import com.dekaustubh.bingo.constants.DI.USER_ID
import com.dekaustubh.bingo.constants.DI.USER_TOKEN
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.db.dao.RoomDao
import com.dekaustubh.bingo.db.dao.UserDao
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
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
    @Named(USER_TOKEN)
    fun provideLoggedInUserToken(bingoDatabase: BingoDatabase): String {
        return Observable.just(bingoDatabase)
            .subscribeOn(Schedulers.computation())
            .map {
                bingoDatabase.userDao().getLoggedInUser(true)?.token ?: ""
            }
            .blockingFirst()
    }

    @Provides
    @Named(USER_ID)
    fun provideLoggedInUserId(bingoDatabase: BingoDatabase): String {
        return Observable.just(bingoDatabase)
            .subscribeOn(Schedulers.computation())
            .map {
                bingoDatabase.userDao().getLoggedInUser(true)?.id ?: ""
            }
            .blockingFirst()
    }
}