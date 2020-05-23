package com.dekaustubh.bingo.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dekaustubh.bingo.databinding.FragmentMainBinding
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.models.Room
import com.dekaustubh.bingo.register.FetchRoomsContract
import com.dekaustubh.bingo.rooms.create.CreateRoomDialogFragment
import dagger.android.support.DaggerFragment
import timber.log.Timber
import java.lang.IllegalStateException
import javax.inject.Inject

class MainFragment : DaggerFragment(), FetchRoomsContract.View {

    private var binding: FragmentMainBinding? = null
    private var onRoomSelectListener: OnRoomSelectListener? = null

    @Inject
    lateinit var presenter: FetchRoomsContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var roomsAdapter: RoomsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initRecyclerView()

        binding?.createRoomView?.setOnClickListener {
            val dialog = CreateRoomDialogFragment.newInstance()
            dialog.show(childFragmentManager, "CreateRoomDialogFragment")
        }

        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is OnRoomSelectListener) {
            throw IllegalStateException("Activity must implement OnRoomSelectedListener")
        }
        onRoomSelectListener = context
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
        binding?.roomsList?.visibility = View.VISIBLE
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
        onRoomSelectListener?.let {
            roomsAdapter.setOnRoomSelectedListener(it)
        }
        with(binding?.roomsList!!) {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(this@MainFragment.requireContext())

            adapter = roomsAdapter
        }
    }

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }
}