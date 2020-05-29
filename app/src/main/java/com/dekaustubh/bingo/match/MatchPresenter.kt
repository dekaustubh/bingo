package com.dekaustubh.bingo.match

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.common.UserRepository
import com.dekaustubh.bingo.constants.DI
import com.dekaustubh.bingo.eventhandlers.EventListener
import com.dekaustubh.bingo.eventhandlers.MatchEventHandler
import com.dekaustubh.bingo.models.Match
import com.dekaustubh.bingo.models.MatchState
import com.dekaustubh.bingo.models.MatchStatus
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
    private val matchEventHandler: MatchEventHandler,
    private val userRepository: UserRepository
) : MatchContract.Presenter, EventListener {

    private val disposable = CompositeDisposable()
    private var view: MatchContract.View? = null

    private var roomId: Long = 0L
    private var matchId: Long = 0L
    private var match: Match? = null
    private lateinit var matchState: MatchState
    private var joinedPeople: Int = 0

    override fun initialize(roomId: Long, matchId: Long) {
        this.roomId = roomId
        this.matchId = matchId
    }

    override fun startMatch() {
        disposable += bingoApi.starteMatch(token, roomId, matchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val match = it?.match ?: throw NullPointerException("Match is empty")
                    view?.showInMatchView(match, matchState)
                },
                {
                    Timber.e(it, "Error while starting match")
                    view?.showError("Something went wrong! Please try again later.")
                }
            )
    }

    private fun joinMatch() {
        if (hasJoinedMatch()) {
            return
        }
        disposable += bingoApi.joinMatch(token, roomId, matchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { matchResult ->
                    matchResult?.match?.let {
                        this.match = it
                        if (it.status == MatchStatus.STARTED) {
                            view?.showInMatchView(it, matchState)
                        }
                    } ?: throw NullPointerException("Match is empty")
                },
                { e ->
                    Timber.e(e, "Error while joining match")
                    view?.showError("Error while joining match")
                }
            )
    }

    override fun attach(view: MatchContract.View) {
        this.view = view
        matchEventHandler.addEventListener(this)
        monitorGameState()
        joinMatch()
    }

    override fun detach() {
        view = null
        disposable.clear()
        matchEventHandler.removeEventListener(this)
    }

    override fun onNewEvent(websocketEvent: WebsocketEvent) {
        Timber.d("Got new event ${websocketEvent.messageType}")
        when (websocketEvent.messageType) {
            MessageType.MATCH_START -> {
                // Match is started
                disposable += bingoApi.getMatchById(token, roomId, matchId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { matchResult ->
                            this.match = matchResult?.match
                            this.match?.let {
                                view?.showInMatchView(it, matchState)
                            }
                        },
                        { e ->
                            Timber.e(e, "Error while getting match")
                            e.message?.let { view?.showError(it) }
                        }
                    )
            }
            MessageType.MATCH_JOIN -> {
                Timber.d("${websocketEvent.userId} joined the match")
                disposable += userRepository.getUserById(websocketEvent.userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { user ->
                            Timber.d("${user?.name} Joined the match")
                            user?.let {
                                joinedPeople++
                                if (joinedPeople > 0) {
                                    view?.showStartMatchView()
                                }
                                view?.showUserJoined(it.name)
                            }
                        },
                        {
                            Timber.e(it, "Error while getting user")
                        }
                    )
            }
            MessageType.MATCH_LEFT -> {
                joinedPeople--
                if (joinedPeople <= 0) {
                    view?.hideStartMatchView()
                } else {
                    disposable += userRepository.getUserById(websocketEvent.userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { user ->
                                Timber.d("${user?.name} left the match")
                                user?.let {
                                    view?.showUserLeft(it.name)
                                }
                            },
                            {
                                Timber.e(it, "Error while getting user")
                            }
                        )
                }
            }
        }
    }

    private fun monitorGameState() {
        matchState = MatchState()
        disposable += Observable.merge(matchState.monitorColumnState(), matchState.monitorRowState())
            .takeUntil(matchState.monitorGameWonState())
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