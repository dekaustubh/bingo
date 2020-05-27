package com.dekaustubh.bingo.models

import com.jakewharton.rxrelay2.PublishRelay

private const val FROM_NUMBER = 1
private const val TO_NUMBER = 99
private const val TOTAL_NUMBERS = 25
private const val ROWS = 5
private const val COLUMNS = 5
private const val TO_WIN = 5

/**
 * Class to capture local match state.
 */
class MatchState {
    // Locally selected numbers.
    private val selectedNumbers = mutableListOf<Int>()
    // Generated numbers.
    private val generatedNumbers = mutableListOf<Int>()
    // Incoming numbers.
    private val incomingNumbers = mutableListOf<Int>()
    private var hasMatchFinished = false
    private var doneRows: Int = 0
    private var doneCols: Int = 0

    private val gameWonState: PublishRelay<Boolean> = PublishRelay.create()
    private val rowState: PublishRelay<Pair<Type, Int>> = PublishRelay.create()
    private val columnState: PublishRelay<Pair<Type, Int>> = PublishRelay.create()

    init {
        generatedNumbers.addAll(
            (FROM_NUMBER..TO_NUMBER).shuffled()
                .take(TOTAL_NUMBERS)
        )
    }

    /**
     * Get initially generated 25 numbers to setup game board.
     */
    fun getGeneratedNumbers() = generatedNumbers

    /**
     * Check if number is already selected.
     */
    fun isNumberAlreadySelected(number: Int) = selectedNumbers.contains(number)

    /**
     * Add number to list once user clicks on it.
     */
    fun selectNumber(number: Int) {
        selectedNumbers.add(number)
        // Also check if user has either completed a row, column or both or won the game.
        tryMarkRowCompleted()
        tryMarkColumnCompleted()
        if (hasGameWon()) {
            gameWonState.accept(true)
        }
    }

    private fun tryMarkRowCompleted(): Int {
        var fromIndex = 0
        var toIndex = ROWS
        for (row in 0 until ROWS) {
            val subList = generatedNumbers.subList(fromIndex, toIndex)
            if (selectedNumbers.containsAll(subList)) {
                doneRows += 1
                rowState.accept(Pair(Type.ROW, row))
                return row
            }
            subList.clear()
            fromIndex = toIndex
            toIndex += 5
        }
        return -1
    }

    private fun tryMarkColumnCompleted(): Int {
        var currentColumn = 0
        for (col in 0 until COLUMNS) {
            val subList = mutableListOf<Int>()
            generatedNumbers.forEachIndexed { index, i ->
                when (currentColumn) {
                    0 -> {
                        if (index == 0 || index == 5 || index == 10 || index == 15 || index == 20) {
                            subList.add(i)
                        }
                    }
                    1 -> {
                        if (index == 1 || index == 6 || index == 11 || index == 16 || index == 21) {
                            subList.add(i)
                        }
                    }
                    2 -> {
                        if (index == 2 || index == 7 || index == 12 || index == 17 || index == 22) {
                            subList.add(i)
                        }
                    }
                    3 -> {
                        if (index == 3 || index == 8 || index == 13 || index == 18 || index == 23) {
                            subList.add(i)
                        }
                    }
                    4 -> {
                        if (index == 4 || index == 9 || index == 14 || index == 19 || index == 24) {
                            subList.add(i)
                        }
                    }
                }
            }
            if (selectedNumbers.containsAll(subList)) {
                doneCols += 1
                columnState.accept(Pair(Type.COLUMN, col))
                return col
            }
            subList.clear()
            currentColumn += 1
        }
        return -1
    }

    fun monitorGameWonState() = gameWonState
    fun monitorRowState() = rowState
    fun monitorColumnState() = columnState

    private fun hasGameWon() = doneRows + doneCols == TO_WIN

    fun clear() {
        doneRows = 0
        doneCols = 0
        selectedNumbers.clear()
        generatedNumbers.clear()
    }

    enum class Type {
        ROW,
        COLUMN
    }
}