package com.dekaustubh.bingo.match

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.models.Room
import dagger.android.DaggerActivity
import timber.log.Timber
import javax.inject.Inject

class StartMatchActivity : DaggerActivity(), StartMatchContract.View {

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.waiting)
    lateinit var waitingTextView: TextView

    @Inject
    lateinit var presenter: StartMatchContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    var room: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        if (!intent.hasExtra(EXTRA_ROOM)) throw RuntimeException("Room extra must be present!!")
        room = intent.getParcelableExtra(EXTRA_ROOM)
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

    override fun startOrUpdateMatch(match: Match) {
        Timber.d("Match created... ${match.id}, ${match.roomId}")
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    companion object {
        const val EXTRA_ROOM = "extra_room"
    }
}
