package com.dekaustubh.bingo.match.join

import android.os.Bundle
import com.dekaustubh.bingo.R
import dagger.android.DaggerActivity

class JoinMatchActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }
}
