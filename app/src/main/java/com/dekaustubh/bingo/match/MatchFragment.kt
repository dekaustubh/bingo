package com.dekaustubh.bingo.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.dekaustubh.bingo.databinding.FragmentMatchBinding
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.models.Match
import com.dekaustubh.bingo.models.MatchState
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MatchFragment : DaggerFragment(), MatchContract.View {

    private var match: Match? = null
    private var binding: FragmentMatchBinding? = null

    @Inject
    lateinit var presenter: MatchContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var numberAdapter: NumberAdapter

    @Inject
    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        match = arguments?.getParcelable(EXTRA_MATCH)
            ?: throw IllegalStateException("Match must be present")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchBinding.inflate(inflater, container, false)
        initRecyclerView()

        binding?.startMatch?.setOnClickListener {
            presenter.startMatch()
        }
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        match?.let {
            presenter.initialize(it.roomId, it.matchId)
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

    override fun showInMatchView(match: Match, state: MatchState) {
        numberAdapter.setNumbers(state.getGeneratedNumbers())
        binding?.inMatchContainer?.visibility = View.VISIBLE
        binding?.beforeMatchContainer?.visibility = View.GONE
    }

    override fun hideStartMatchView() {
        binding?.beforeMatchContainer?.visibility = View.VISIBLE
        binding?.startMatch?.visibility = View.GONE
    }

    override fun showStartMatchView() {
        binding?.beforeMatchContainer?.visibility = View.VISIBLE
        binding?.startMatch?.visibility = View.VISIBLE
    }

    override fun showUserJoined(name: String) {
        userAdapter.addUser(name)
    }

    override fun showUserLeft(name: String) {
        userAdapter.removeUser(name)
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    private fun initRecyclerView() {
        with (binding?.numberGrid!!) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this.context, 5, GridLayoutManager.VERTICAL, false)

            adapter = numberAdapter
        }

        with (binding?.joinedList!!) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, VERTICAL, false)

            adapter = userAdapter
        }
    }

    companion object {
        const val EXTRA_MATCH = "extra_match"
        const val TAG = "MatchFragment"

        fun newInstance(match: Match) = MatchFragment().apply {
            val bundle = Bundle().apply {
                putParcelable(EXTRA_MATCH, match)
            }
            arguments = bundle
        }
    }
}
