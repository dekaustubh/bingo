package com.dekaustubh.bingo.rooms.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dekaustubh.bingo.databinding.FragmentCreateRoomBinding
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.models.Room
import dagger.android.support.DaggerDialogFragment
import timber.log.Timber
import javax.inject.Inject

class CreateRoomDialogFragment : DaggerDialogFragment(), CreateRoomContract.View {

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var presenter: CreateRoomContract.Presenter

    private var binding: FragmentCreateRoomBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentCreateRoomBinding.inflate(inflater, container, false)

        binding?.create?.setOnClickListener {
            presenter.createRoom(binding?.name.toString())
        }

        binding?.cancel?.setOnClickListener {
            dismiss()
        }
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun showRoom(room: Room) {
        Timber.d("Room ${room.name} created successfully!")
        dismiss()
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    companion object {
        fun newInstance() = CreateRoomDialogFragment()
    }
}
