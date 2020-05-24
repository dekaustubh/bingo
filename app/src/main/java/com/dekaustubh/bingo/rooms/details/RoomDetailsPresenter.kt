package com.dekaustubh.bingo.rooms.details

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.constants.DI
import com.dekaustubh.bingo.eventhandlers.EventListener
import com.dekaustubh.bingo.eventhandlers.MatchEventHandler
import com.dekaustubh.bingo.eventhandlers.UserEventHandler
import com.dekaustubh.bingo.websockets.BingoSocketListener
import com.dekaustubh.bingo.websockets.WebsocketEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.WebSocketListener
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class RoomDetailsPresenter @Inject constructor(
    private val bingoApi: BingoApi,
    @Named(DI.USER_TOKEN) private val token: String,
    private val matchEventHandler: MatchEventHandler,
    private val userEventHandler: UserEventHandler
) : RoomDetailsContract.Presenter, EventListener {

    private val compositeDisposable = CompositeDisposable()
    private var view: RoomDetailsContract.View? = null

    override fun fetchMatchesForRoom(roomId: Long) {
        compositeDisposable.add(
            bingoApi.getRoomsMatches(token, roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { matchesResult ->
                        view?.showMatches(matchesResult.matches)
                    },
                    { e ->
                        Timber.e(e)
                        view?.showError(e.message ?: "Error while fetching room")
                    }
                )
        )
    }

    override fun attach(view: RoomDetailsContract.View) {
        this.view = view
        matchEventHandler.addEventListener(this)
    }

    override fun detach() {
        matchEventHandler.removeEventListener(this)
        compositeDisposable.clear()
        view = null
    }

    override fun onNewEvent(websocketEvent: WebsocketEvent) {
        Timber.d("Got new event ${websocketEvent.messageType}")
    }
}