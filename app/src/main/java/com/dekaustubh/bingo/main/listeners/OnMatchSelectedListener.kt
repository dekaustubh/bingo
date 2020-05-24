package com.dekaustubh.bingo.main.listeners

import com.dekaustubh.bingo.models.Match

interface OnMatchSelectedListener {
    fun onMatchSelected(match: Match)
}