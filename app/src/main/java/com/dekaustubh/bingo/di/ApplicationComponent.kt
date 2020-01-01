package com.dekaustubh.bingo.di

import android.app.Application
import com.dekaustubh.bingo.BingoApplication
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.db.dao.RoomDao
import com.dekaustubh.bingo.db.dao.UserDao
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DatabaseModule::class
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<BingoApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun databaseModule(databaseModule: DatabaseModule): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(instance: BingoApplication?)
}