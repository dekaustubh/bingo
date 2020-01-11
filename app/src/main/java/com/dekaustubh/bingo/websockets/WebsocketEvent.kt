package com.dekaustubh.bingo.websockets

import com.google.gson.annotations.SerializedName

sealed class WebsocketEvent(
    @SerializedName("message_type")
    val messageType: MessageType
)

data class UserConnected(
    @SerializedName("user_id")
    val userId: Long
): WebsocketEvent(MessageType.CONNECT)

data class UserJoined(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long
) : WebsocketEvent(MessageType.JOIN)

data class MatchStarted(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long
) : WebsocketEvent(MessageType.START)

data class TurnTaken(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("next_turn")
    val nextTurn: Long,
    val number: Int
) : WebsocketEvent(MessageType.TAKE_TURN)

data class MatchWon(
    @SerializedName("user_id")
    val userId: Long,
    val points: Int,
    @SerializedName("room_id")
    val roomId: Long
) : WebsocketEvent(MessageType.WIN)