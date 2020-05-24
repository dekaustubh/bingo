package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.main.MainFragment
import com.dekaustubh.bingo.match.create.CreateMatchFragment
import com.dekaustubh.bingo.match.MatchFragment
import com.dekaustubh.bingo.register.RegisterActivity
import com.dekaustubh.bingo.register.RegisterFragment
import com.dekaustubh.bingo.rooms.create.CreateRoomDialogFragment
import com.dekaustubh.bingo.rooms.details.RoomDetailsFragment
import com.dekaustubh.bingo.services.BingoFirebaseMessagingService
import com.dekaustubh.bingo.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector()
    abstract fun contributeCreateRoomActivity(): CreateRoomDialogFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateMatchFragment(): CreateMatchFragment

    @ContributesAndroidInjector()
    abstract fun contributeJoinMatchActivity(): MatchFragment

    @ContributesAndroidInjector()
    abstract fun contributeRoomDetailsFragment(): RoomDetailsFragment

    @ContributesAndroidInjector()
    abstract fun contributeBingoFirebaseMessagingService(): BingoFirebaseMessagingService
}