package com.dekaustubh.bingo.match.join

import android.os.Bundle
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.match.Match
import dagger.android.DaggerActivity
import timber.log.Timber
import javax.inject.Inject

class JoinMatchActivity : DaggerActivity(), JoinMatchContract.View {

    var match: Match? = null

    @Inject
    lateinit var presenter: JoinMatchContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        match = intent.getParcelableExtra(EXTRA_MATCH)
        if (match == null) {
            Timber.e("Match mush be present")
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        match?.let {
            presenter.joinMatch(it.roomId, it.id)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    companion object {
        const val EXTRA_MATCH = "extra_match"
    }
}
