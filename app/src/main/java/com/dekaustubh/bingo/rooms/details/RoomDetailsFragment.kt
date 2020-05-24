package com.dekaustubh.bingo.rooms.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dekaustubh.bingo.databinding.FragmentRoomsDetailsBinding
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.main.listeners.OnMatchSelectedListener
import com.dekaustubh.bingo.match.create.CreateMatchFragment
import com.dekaustubh.bingo.models.Match
import com.dekaustubh.bingo.models.Room
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class RoomDetailsFragment : DaggerFragment(), RoomDetailsContract.View {

    @Inject
    lateinit var presenter: RoomDetailsContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var roomDetailsAdapter: RoomsDetailsAdapter

    private var binding: FragmentRoomsDetailsBinding? = null
    private var onMatchSelectedListener: OnMatchSelectedListener? = null

    var room: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        room = arguments?.getParcelable(CreateMatchFragment.EXTRA_ROOM)
            ?: throw IllegalArgumentException("Room extra must be present")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomsDetailsBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is OnMatchSelectedListener) {
            throw IllegalStateException("Host activity should implement onMatchSelectedListener")
        }
        onMatchSelectedListener = context as OnMatchSelectedListener
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        presenter.fetchMatchesForRoom(room!!.id)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun showMatches(matches: List<Match>) {
        matches.forEach {
            Timber.d("Match => ${it.id}")
        }
        roomDetailsAdapter.setMatches(matches)
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    override fun newMatchCreated(match: Match, userName: String) {
        Timber.d("New match created with id : ${match.id}")
        toaster.showToast("$userName created new match")
    }

    private fun initRecyclerView() {
        onMatchSelectedListener?.let {
            roomDetailsAdapter.setOnMatchSelectedListener(it)
        }
        with(binding?.matchesList!!) {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(this@RoomDetailsFragment.requireContext())

            adapter = roomDetailsAdapter
        }
    }

    companion object {
        const val EXTRA_ROOM = "extra_room"
        const val TAG = "RoomDetailsFragment"

        fun newInstance(room: Room) = RoomDetailsFragment().apply {
            val bundle = Bundle().apply {
                putParcelable(EXTRA_ROOM, room)
            }
            arguments = bundle
        }
    }
}
