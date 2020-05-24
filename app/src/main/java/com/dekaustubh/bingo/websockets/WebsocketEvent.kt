package com.dekaustubh.bingo.websockets

import com.dekaustubh.bingo.models.User
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

sealed class WebsocketEvent(
    @SerializedName("message_type")
    val messageType: MessageType
)

data class UserConnected(
    @SerializedName("user_id")
    val userId: String
): WebsocketEvent(MessageType.CONNECT)

data class MatchJoined(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("room_id")
    val roomId: Long
) : WebsocketEvent(MessageType.MATCH_JOIN)

data class MatchLeft(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("room_id")
    val roomId: Long
) : WebsocketEvent(MessageType.MATCH_LEFT)

data class MatchCreated(
    @JsonProperty("user_id")
    val userId: String,
    @JsonProperty("user_name")
    val userName: String,
    @JsonProperty("match_id")
    val matchId: Long,
    @JsonProperty("room_id")
    val roomId: Long
) : WebsocketEvent(MessageType.MATCH_CREATE)

data class MatchStarted(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @JsonProperty("room_id")
    val roomId: Long
) : WebsocketEvent(MessageType.MATCH_START)

data class MatchTurn(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @JsonProperty("room_id")
    val roomId: Long,
    @SerializedName("next_turn")
    val nextTurn: User?,
    val number: Int
) : WebsocketEvent(MessageType.MATCH_TURN)

data class MatchWon(
    @SerializedName("user_id")
    val userId: String,
    val points: Int,
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("room_id")
    val roomId: Long
) : WebsocketEvent(MessageType.MATCH_WON)