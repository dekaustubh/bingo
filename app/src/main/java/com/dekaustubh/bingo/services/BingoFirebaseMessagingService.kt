package com.dekaustubh.bingo.services

import android.annotation.SuppressLint
import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.common.UserRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Firebase service for device token management & notification management.
 */
class BingoFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var bingoApi: BingoApi
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()
        // Since this is not a {DaggerService}, inject manually!
        AndroidInjection.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("onMessageReceived : From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Timber.d("onMessageReceived : Message data payload: ${remoteMessage.data}")
        }
    }

    @SuppressLint("CheckResult")
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Timber.d("Firebase service New token $token")
        userRepository.getLoggedInUser()
            .subscribeOn(Schedulers.io())
            .flatMap { user ->
                bingoApi.updateDevice(token, user.token)
            }
            .subscribe(
                {
                    Timber.d("Device updation successful!")
                },
                { e ->
                    Timber.e(e, "Error while updating device ID.")

                }
            )
    }
}