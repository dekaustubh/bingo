package com.dekaustubh.bingo.models

import android.os.Parcelable
import com.dekaustubh.bingo.db.entities.DbRoom
import com.dekaustubh.bingo.match.Match
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(
    val id: Long,
    val name: String,
    @SerializedName("leaderboard_id")
    val leaderboardId: Long?,
    @SerializedName("created_by")
    val createdBy: Long,
    val matches: List<Match>?
) : Parcelable

fun Room.toDbRoom(): DbRoom {
    return DbRoom(
        id = id,
        name = name,
        leaderboardId = leaderboardId,
        createdBy = createdBy
    )
}