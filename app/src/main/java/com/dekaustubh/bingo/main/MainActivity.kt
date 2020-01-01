package com.dekaustubh.bingo.main

import android.os.Bundle
import com.dekaustubh.bingo.R
import dagger.android.DaggerActivity

class MainActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
