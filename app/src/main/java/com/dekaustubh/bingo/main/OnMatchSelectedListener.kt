package com.dekaustubh.bingo.main

import com.dekaustubh.bingo.match.Match

interface OnMatchSelectedListener {
    fun onMatchSelected(match: Match)
}