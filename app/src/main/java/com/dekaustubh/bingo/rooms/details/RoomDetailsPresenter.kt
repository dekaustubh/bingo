package com.dekaustubh.bingo.rooms.details

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.constants.DI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class RoomDetailsPresenter @Inject constructor(
    private val bingoApi: BingoApi,
    @Named(DI.USER_TOKEN) private val token: String
) : RoomDetailsContract.Presenter {

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
    }

    override fun detach() {
        compositeDisposable.clear()
        view = null
    }

}