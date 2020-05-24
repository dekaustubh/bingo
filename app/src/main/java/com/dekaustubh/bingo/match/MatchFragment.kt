package com.dekaustubh.bingo.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dekaustubh.bingo.databinding.FragmentMatchBinding
import com.dekaustubh.bingo.helpers.Toaster
import com.dekaustubh.bingo.models.Match
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MatchFragment : DaggerFragment(), MatchContract.View {

    private var match: Match? = null
    private var binding: FragmentMatchBinding? = null

    @Inject
    lateinit var presenter: MatchContract.Presenter

    @Inject
    lateinit var toaster: Toaster

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
        return binding?.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    companion object {
        const val EXTRA_MATCH = "extra_match"
        const val TAG = "MatchFragment"

        fun newInstance(match: Match) = MatchFragment()
            .apply {
            val bundle = Bundle().apply {
                putParcelable(EXTRA_MATCH, match)
            }
            arguments = bundle
        }
    }
}
