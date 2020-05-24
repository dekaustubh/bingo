package com.dekaustubh.bingo.websockets

import com.dekaustubh.bingo.models.User
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

sealed class WebsocketEvent(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("message_type")
    val messageType: MessageType
)

data class UserConnected(val id: String): WebsocketEvent(id, MessageType.CONNECT)

data class MatchJoined(
    val id: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("room_id")
    val roomId: Long
) : WebsocketEvent(id, MessageType.MATCH_JOIN)

data class MatchLeft(
    val id: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("room_id")
    val roomId: Long
) : WebsocketEvent(id, MessageType.MATCH_LEFT)

data class MatchCreated(
    val id: String,
    @JsonProperty("user_name")
    val userName: String,
    @JsonProperty("match_id")
    val matchId: Long,
    @JsonProperty("room_id")
    val roomId: Long
) : WebsocketEvent(id, MessageType.MATCH_CREATE)

data class MatchStarted(
    val id: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @JsonProperty("room_id")
    val roomId: Long
) : WebsocketEvent(id, MessageType.MATCH_START)

data class MatchTurn(
    val id: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("match_id")
    val matchId: Long,
    @JsonProperty("room_id")
    val roomId: Long,
    @SerializedName("next_turn")
    val nextTurn: User?,
    val number: Int
) : WebsocketEvent(id, MessageType.MATCH_TURN)

data class MatchWon(
    val id: String,
    val points: Int,
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("room_id")
    val roomId: Long
) : WebsocketEvent(id, MessageType.MATCH_WON)