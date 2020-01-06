package com.dekaustubh.bingo.match

import com.dekaustubh.bingo.models.results.Success
import com.google.gson.annotations.SerializedName

data class Match(
    val id: Long,
    @SerializedName("room_id")
    val roomId: Long,
    @SerializedName("created_by")
    val createdBy: Long,
    val players: MutableList<Long>,
    @SerializedName("winner_id")
    val winnerId: Long,
    val status: String
)

data class TakeTurn(
    val number: Int,
    @SerializedName("current_turn")
    val currentTaker: Long,
    @SerializedName("next_turn")
    val nextTaker: Long
)

data class MatchResult(
    val error: Error? = null,
    val success: Success? = null,
    val match: Match?
)