package com.dekaustubh.bingo.rooms.create

import android.os.Bundle
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.databinding.ActivityCreateRoomBinding
import com.dekaustubh.bingo.models.Room
import dagger.android.DaggerActivity
import javax.inject.Inject

class CreateRoomActivity : DaggerActivity(), CreateRoomContract.View {

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var presenter: CreateRoomContract.Presenter

    private var binding: ActivityCreateRoomBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRoomBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.create?.setOnClickListener {
            presenter.createRoom(binding?.name.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun showRoom(room: Room) {
        toaster.showToast("Room ${room.name} created successfully!")

        finish()
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }
}
