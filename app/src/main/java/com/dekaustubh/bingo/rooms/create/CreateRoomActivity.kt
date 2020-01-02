package com.dekaustubh.bingo.rooms.create

import android.os.Bundle
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.models.Room
import dagger.android.DaggerActivity
import javax.inject.Inject

class CreateRoomActivity : DaggerActivity(), CreateRoomContract.View {

    @BindView(R.id.name)
    lateinit var roomNameEditText: EditText

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var presenter: CreateRoomContract.Presenter

    @OnClick(R.id.create)
    fun onCreateRoomClicked() {
        presenter.createRoom(roomNameEditText.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)
        ButterKnife.bind(this)
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
