package com.dekaustubh.bingo.main.listeners

import com.dekaustubh.bingo.match.Match

interface OnMatchSelectedListener {
    fun onMatchSelected(match: Match)
}