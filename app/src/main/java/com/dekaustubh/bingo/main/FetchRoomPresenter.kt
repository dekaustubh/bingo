package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.rooms.create.RoomRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FetchRoomPresenter @Inject constructor(
    private val roomRepository: RoomRepository
) : FetchRoomsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: FetchRoomsContract.View? = null

    override fun fetchRoomById(roomId: Long) {
        compositeDisposable.add(
            roomRepository.getRoomById(roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { room ->
                        view?.showRoom(room)
                    },
                    { e ->
                        Timber.e(e)
                        view?.showError(e.message ?: "Error while fetching room")
                    }
                )
        )
    }

    override fun fetchRooms() {
        compositeDisposable.add(
            roomRepository.getRooms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { rooms ->
                        view?.showRooms(rooms)
                    },
                    { e ->
                        Timber.e(e)
                        view?.showError(e.message ?: "Error while fetching rooms")
                    }
                )
        )
    }

    override fun attach(view: FetchRoomsContract.View) {
        this.view = view
    }

    override fun detach() {
        compositeDisposable.clear()
        view = null
    }
}