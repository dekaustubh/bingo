package com.dekaustubh.bingo.rooms.create

import com.dekaustubh.bingo.rooms.RoomRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CreateRoomPresenter @Inject constructor(
    private val roomRepository: RoomRepository
): CreateRoomContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    private var view: CreateRoomContract.View? = null

    override fun createRoom(name: String) {
        compositeDisposable.add(
            roomRepository.createRoom(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { room ->
                        view?.showRoom(room)
                    },
                    { e ->
                        Timber.e(e)
                        view?.showError(e.message.toString())
                    }
                )
        )
    }

    override fun attach(view: CreateRoomContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
        compositeDisposable.clear()
    }
}