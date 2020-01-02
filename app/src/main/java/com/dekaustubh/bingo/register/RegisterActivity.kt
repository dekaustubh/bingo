package com.dekaustubh.bingo.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dekaustubh.bingo.R


class RegisterActivity : AppCompatActivity(), RegisterFragment.OnRegisterFragmentListener,
    LoginFragment.OnLoginFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        showRegisterFragment()
    }

    override fun clickedLogin() {
        showLoginFragment()
    }

    override fun clickedRegister() {
        showRegisterFragment()
    }

    private fun showLoginFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            0,
            0
        )
        transaction.replace(R.id.container, LoginFragment.newInstance())
        transaction.commit()
    }

    private fun showRegisterFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_left,
            R.anim.exit_to_right,
            0,
            0
        )
        transaction.replace(R.id.container, RegisterFragment.newInstance())
        transaction.commit()
    }
}
