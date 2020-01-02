package com.dekaustubh.bingo.rooms.create

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class CreateRoomPresenter @Inject constructor(
    private val roomRepository: RoomRepository
): CreateRoomContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    private var view: CreateRoomContract.View? = null

    override fun createRoom(name: String) {
        compositeDisposable.add(
            roomRepository.createRoom(name)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        val room = it?.room
                        if (it.error != null) throw Exception(it.error.error)
                        if (room == null) throw Exception("Error while getting room")

                        view?.showRoom(room)
                    },
                    {
                        Timber.e(it)
                        view?.showError(it.message.toString())
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