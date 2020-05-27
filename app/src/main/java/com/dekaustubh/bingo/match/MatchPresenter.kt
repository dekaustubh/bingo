package com.dekaustubh.bingo.match

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.constants.DI
import com.dekaustubh.bingo.eventhandlers.EventListener
import com.dekaustubh.bingo.eventhandlers.MatchEventHandler
import com.dekaustubh.bingo.helpers.Vacant
import com.dekaustubh.bingo.models.Match
import com.dekaustubh.bingo.models.MatchState
import com.dekaustubh.bingo.utils.plusAssign
import com.dekaustubh.bingo.websockets.MessageType
import com.dekaustubh.bingo.websockets.WebsocketEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class MatchPresenter @Inject constructor(
    private val bingoApi: BingoApi,
    @Named(DI.USER_TOKEN) private val token: String,
    @Named(DI.USER_ID) private val loggedInUserId: String,
    private val matchEventHandler: MatchEventHandler
) : MatchContract.Presenter, EventListener {

    private val disposable = CompositeDisposable()
    private var view: MatchContract.View? = null

    private var roomId: Long = 0L
    private var matchId: Long = 0L
    private var match: Match? = null
    private var matchState: MatchState? = null

    override fun initialize(roomId: Long, matchId: Long) {
        this.roomId = roomId
        this.matchId = matchId
    }

    private fun joinMatch() {
        if (hasJoinedMatch()) {
            return
        }
        disposable.add(
            bingoApi.joinMatch(token, roomId, matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { matchResult ->
                        this.match = matchResult?.match
                    },
                    { e ->
                        Timber.e(e, "Error while joining match")
                        e.message?.let { view?.showError(it) }
                    }
                )
        )
    }

    override fun attach(view: MatchContract.View) {
        this.view = view
        matchEventHandler.addEventListener(this)
        joinMatch()
    }

    override fun detach() {
        disposable.clear()
        matchEventHandler.removeEventListener(this)
    }

    override fun onNewEvent(websocketEvent: WebsocketEvent) {
        Timber.d("Got new event ${websocketEvent.messageType}")
        when (websocketEvent.messageType) {
            MessageType.MATCH_START -> {
                // Match is started
                val matchStarted = websocketEvent
                disposable += bingoApi.getMatchById(token, roomId, matchId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { matchResult ->
                            this.match = matchResult?.match
                            this.match?.let {
                                monitorGameState()
                                view?.showInMatchView(it, matchState!!)
                            }
                        },
                        { e ->
                            Timber.e(e, "Error while getting match")
                            e.message?.let { view?.showError(it) }
                        }
                    )
            }
        }
    }

    private fun monitorGameState() {
        if (matchState == null) matchState = MatchState()

        disposable += Observable.merge(matchState!!.monitorColumnState(), matchState!!.monitorRowState())
            .takeUntil(matchState!!.monitorGameWonState())
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state ->
                    val which = state.second
                    if (state.first == MatchState.Type.ROW) {
                        // Row changed
                    } else {
                        // Column changed
                    }
                },
                { e ->
                    Timber.e(e, "Error in monitor game won state")
                }
            )
    }

    private fun hasJoinedMatch(): Boolean {
        if (match == null || match?.players?.contains(loggedInUserId) == false) return false
        return true
    }
}