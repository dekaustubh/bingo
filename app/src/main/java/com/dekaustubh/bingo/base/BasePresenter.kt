package com.dekaustubh.bingo.base

interface BasePresenter<in T> {
    fun attach(view: T)
    fun detach()
}