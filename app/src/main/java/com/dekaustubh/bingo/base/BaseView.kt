package com.dekaustubh.bingo.base

interface BaseView<out T> {
    fun showError(message: String)
}