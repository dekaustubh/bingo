package com.dekaustubh.bingo.register

import android.os.Bundle
import com.dekaustubh.bingo.R
import dagger.android.DaggerActivity

class RegisterActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}
