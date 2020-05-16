package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.match.create.CreateMatchActivity
import com.dekaustubh.bingo.match.join.JoinMatchActivity
import com.dekaustubh.bingo.register.RegisterActivity
import com.dekaustubh.bingo.register.RegisterFragment
import com.dekaustubh.bingo.rooms.create.CreateRoomActivity
import com.dekaustubh.bingo.rooms.details.RoomDetailsActivity
import com.dekaustubh.bingo.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector()
    abstract fun contributeCreateRoomActivity(): CreateRoomActivity

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateMatchActivity(): CreateMatchActivity

    @ContributesAndroidInjector()
    abstract fun contributeJoinMatchActivity(): JoinMatchActivity

    @ContributesAndroidInjector()
    abstract fun contributeRoomDetailsActivity(): RoomDetailsActivity
}