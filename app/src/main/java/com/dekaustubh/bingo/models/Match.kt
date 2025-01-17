package com.dekaustubh.bingo.models

import android.os.Parcelable
import com.dekaustubh.bingo.models.results.Success
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    val id: Long,
    @SerializedName("room_id")
    val roomId: Long,
    @SerializedName("created_by")
    val createdBy: String,
    val players: MutableList<String>,
    @SerializedName("winner_id")
    val winnerId: String,
    @SerializedName("status")
    val status: MatchStatus,
    @SerializedName("match_id")
    val matchId: Long
) : Parcelable

enum class MatchStatus {
    WAITING,
    STARTED,
    ENDED
}

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

data class MatchesResult(
    val error: Error? = null,
    val success: Success? = null,
    val matches: List<Match>
)