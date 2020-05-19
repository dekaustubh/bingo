package com.dekaustubh.bingo.main

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.websockets.WebSocketCloseCode

interface MainContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun connectToWebSocket()
        fun disconnectToWebSocket(code: WebSocketCloseCode)
    }
}