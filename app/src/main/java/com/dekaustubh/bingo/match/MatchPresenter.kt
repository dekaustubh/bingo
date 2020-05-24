package com.dekaustubh.bingo.match

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.constants.DI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class MatchPresenter @Inject constructor(
    private val bingoApi: BingoApi,
    @Named(DI.USER_TOKEN) private val token: String
) : MatchContract.Presenter {

    private val disposable = CompositeDisposable()
    private var view : MatchContract.View? = null

    override fun joinMatch(roomId: Long, matchId: Long) {
        disposable.add(
            bingoApi.dummy(token, roomId, matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { matchResult ->
                        val match = matchResult?.match
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
    }

    override fun detach() {
        disposable.clear()
    }
}