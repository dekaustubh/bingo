package com.dekaustubh.bingo.main

import android.os.Bundle
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.databinding.ActivityMainBinding
import com.dekaustubh.bingo.main.listeners.OnMatchSelectedListener
import com.dekaustubh.bingo.main.listeners.OnRoomSelectListener
import com.dekaustubh.bingo.main.listeners.OnStartNewMatchListener
import com.dekaustubh.bingo.models.Match
import com.dekaustubh.bingo.match.create.CreateMatchFragment
import com.dekaustubh.bingo.match.MatchFragment
import com.dekaustubh.bingo.models.Room
import com.dekaustubh.bingo.rooms.details.RoomDetailsFragment
import com.dekaustubh.bingo.websockets.WebSocketCloseCode
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(),
    OnRoomSelectListener,
    OnMatchSelectedListener,
    OnStartNewMatchListener {

    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        showMainFragment()
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.connectToWebSocket()
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.disconnectToWebSocket(WebSocketCloseCode.NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onRoomSelected(room: Room) {
        val fragment = RoomDetailsFragment.newInstance(room)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, RoomDetailsFragment.TAG)
        transaction.commit()
    }

    override fun onMatchSelected(match: Match) {
        val fragment = MatchFragment.newInstance(match)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, MatchFragment.TAG)
        transaction.commit()
    }

    override fun onNewMatchStarted(room: Room) {
        val fragment = CreateMatchFragment.newInstance(room)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, RoomDetailsFragment.TAG)
        transaction.commit()
    }

    private fun showMainFragment() {
        with (supportFragmentManager.beginTransaction()) {
            val fragment = MainFragment.newInstance()
            replace(R.id.container, fragment, MainFragment.TAG)
                .addToBackStack(null)
                .commit()

        }
    }
}
