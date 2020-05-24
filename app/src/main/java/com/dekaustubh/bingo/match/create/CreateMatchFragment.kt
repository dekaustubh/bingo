package com.dekaustubh.bingo.match.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dekaustubh.bingo.databinding.FragmentCreateMatchBinding
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.models.Match
import com.dekaustubh.bingo.models.Room
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class CreateMatchFragment : DaggerFragment(), CreateMatchContract.View {

    @Inject
    lateinit var presenter: CreateMatchContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    private var binding: FragmentCreateMatchBinding? = null
    private var room: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        room = arguments?.getParcelable(EXTRA_ROOM)
            ?: throw RuntimeException("Room extra must be present!!")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateMatchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        room?.let {
            presenter.startMatchForRoom(it)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun startOrUpdateMatch(match: Match) {
        Timber.d("Match created... ${match.id}, ${match.roomId}")
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    companion object {
        const val EXTRA_ROOM = "extra_room"
        const val TAG = "CreateMatchFragment"

        fun newInstance(room: Room) = CreateMatchFragment().apply {
            val bundle = Bundle().apply {
                putParcelable(EXTRA_ROOM, room)
            }
            arguments = bundle
        }
    }
}
