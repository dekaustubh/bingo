package com.dekaustubh.bingo.main

import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.models.Room
import com.dekaustubh.bingo.register.FetchRoomsContract
import com.dekaustubh.bingo.rooms.create.CreateRoomActivity
import dagger.android.DaggerActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerActivity(), FetchRoomsContract.View {

    @Inject
    lateinit var presenter: FetchRoomsContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    @OnClick(R.id.create_room)
    fun onCreateRoomClicked() {
        startActivity(Intent(this, CreateRoomActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)

        presenter.fetchRooms()
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun showRoom(room: Room) {
        Timber.d("Room ==> ${room.name}, ${room.createdBy}")
    }

    override fun showRooms(rooms: List<Room>) {
        if (rooms.isEmpty()) {
            toaster.showToast("No rooms found.")
            return
        }
        rooms.forEach {
            Timber.d("Room ==> ${it.name}, ${it.createdBy}")
        }
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }
}
