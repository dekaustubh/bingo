package com.dekaustubh.bingo.rooms.details

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.main.RoomsAdapter
import com.dekaustubh.bingo.match.Match
import com.dekaustubh.bingo.match.create.CreateMatchActivity
import com.dekaustubh.bingo.models.Room
import com.dekaustubh.bingo.register.FetchRoomsContract
import dagger.android.DaggerActivity
import timber.log.Timber
import javax.inject.Inject

class RoomDetailsActivity : DaggerActivity(), RoomDetailsContract.View {

    @Inject
    lateinit var presenter: RoomDetailsContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var roomDetailsAdapter: RoomsDetailsAdapter

    @BindView(R.id.matches)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.no_matches)
    lateinit var noMatchesTextView: TextView

    var room: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms_details)
        ButterKnife.bind(this)

        initRecyclerView()

        if (!intent.hasExtra(EXTRA_ROOM)) throw RuntimeException("Room extra must be present!!")
        room = intent.getParcelableExtra(CreateMatchActivity.EXTRA_ROOM)
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
        noMatchesTextView.visibility = View.GONE
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(this@RoomDetailsActivity)

            adapter = roomDetailsAdapter
        }
    }

    companion object {
        const val EXTRA_ROOM = "extra_room"
    }
}
