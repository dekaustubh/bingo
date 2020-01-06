package com.dekaustubh.bingo.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
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

    @Inject
    lateinit var roomsAdapter: RoomsAdapter

    @BindView(R.id.rooms)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.no_rooms)
    lateinit var noRoomsTextView: TextView

    @OnClick(R.id.create_room)
    fun onCreateRoomClicked() {
        startActivity(Intent(this, CreateRoomActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

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

    override fun showRoom(room: Room) {
        Timber.d("Room ==> ${room.name}, ${room.createdBy}")
    }

    override fun showRooms(rooms: List<Room>) {
        if (rooms.isEmpty()) {
            toaster.showToast("No rooms found.")
            return
        }
        roomsAdapter.setRooms(rooms)
        noRoomsTextView.visibility = View.GONE
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(this@MainActivity)

            adapter = roomsAdapter
        }
    }
}
