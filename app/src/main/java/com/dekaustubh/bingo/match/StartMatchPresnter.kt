package com.dekaustubh.bingo.match

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.constants.DI
import com.dekaustubh.bingo.models.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class StartMatchPresenterImpl @Inject constructor(
    private val bingoApi: BingoApi,
    @Named(DI.USER_TOKEN) private val token: String
) : StartMatchContract.Presenter {

    private var view: StartMatchContract.View? = null
    private val disposable = CompositeDisposable()

    override fun startMatchForRoom(room: Room) {
        disposable.add(
            bingoApi.startMatchForRoom(token, room.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { matchResult ->
                        matchResult?.match?.let {
                            view?.startOrUpdateMatch(it)
                        }
                    },
                    { e ->
                        Timber.e(e)
                        view?.showError(e.message.toString())
                    }
                )
        )
    }

    override fun attach(view: StartMatchContract.View) {
        this.view = view
    }

    override fun detach() {
        disposable.clear()
        view = null
    }

}