package com.dekaustubh.bingo.splash

import android.content.Intent
import android.os.Bundle
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.preferences.LoginPreference
import com.dekaustubh.bingo.register.RegisterActivity
import dagger.android.DaggerActivity
import javax.inject.Inject

class SplashActivity : DaggerActivity() {

    @Inject
    lateinit var loginPref: LoginPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (loginPref.isUsedLoggedIn()) {
            startActivity(Intent(this, RegisterActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }

        finish()
    }
}
