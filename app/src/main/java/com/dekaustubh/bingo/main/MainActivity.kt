package com.dekaustubh.bingo.main

import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.rooms.create.CreateRoomActivity
import dagger.android.DaggerActivity

class MainActivity : DaggerActivity() {

    @OnClick(R.id.create_room)
    fun onCreateRoomClicked() {
        startActivity(Intent(this, CreateRoomActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
    }


}
