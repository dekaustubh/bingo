package com.dekaustubh.bingo.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.databinding.ActivityMainBinding
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

    @Inject
    lateinit var roomsAdapter: RoomsAdapter

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.createRoomView?.setOnClickListener {
            startActivity(Intent(this, CreateRoomActivity::class.java))
        }
        initRecyclerView()
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun showRoom(room: Room) {
        Timber.d("Room ==> ${room.name}, ${room.createdBy}")
    }

    override fun showRooms(rooms: List<Room>) {
        if (rooms.isEmpty()) {
            toaster.showToast("No rooms found.")
            return
        }
        roomsAdapter.setRooms(rooms)
        binding?.noRoomsTextView?.visibility = View.GONE
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    private fun initRecyclerView() {
        with(binding?.roomsList!!) {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(this@MainActivity)

            adapter = roomsAdapter
        }
    }
}
