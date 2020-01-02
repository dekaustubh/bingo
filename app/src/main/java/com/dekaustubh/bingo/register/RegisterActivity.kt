package com.dekaustubh.bingo.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dekaustubh.bingo.R


class RegisterActivity : AppCompatActivity(), RegisterFragment.OnRegisterFragmentListener,
    LoginFragment.OnLoginFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        replaceFragment(RegisterFragment.newInstance())
    }

    override fun clickedLogin() {
        replaceFragment(LoginFragment.newInstance())
    }

    override fun clickedRegister() {
        replaceFragment(RegisterFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
