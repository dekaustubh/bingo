package com.dekaustubh.bingo.rooms.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dekaustubh.bingo.databinding.FragmentRoomsDetailsBinding
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.match.Match
import com.dekaustubh.bingo.match.create.CreateMatchActivity
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

    var room: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        room = arguments?.getParcelable(CreateMatchActivity.EXTRA_ROOM)
            ?: throw IllegalArgumentException("Room extra must be present")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomsDetailsBinding.inflate(layoutInflater)
        initRecyclerView()
        return binding?.root
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

    private fun initRecyclerView() {
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
