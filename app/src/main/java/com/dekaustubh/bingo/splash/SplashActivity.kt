package com.dekaustubh.bingo.splash

import android.content.Intent
import android.os.Bundle
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.preferences.UserPreference
import com.dekaustubh.bingo.register.RegisterActivity
import dagger.android.DaggerActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashActivity : DaggerActivity(), SplashContract.View {

    private val scope = MainScope()

    @Inject
    lateinit var presenter: SplashContract.Presenter
    @Inject
    lateinit var toaster: Toaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        presenter.getLoggedInUser()
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun showRegisterScreen() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    override fun showHomeScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
