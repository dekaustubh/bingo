package com.dekaustubh.bingo.websockets

enum class MessageType(val value: String) {
    MATCH_JOIN("match_join"),
    MATCH_CREATE("match_create"),
    MATCH_START("match_start"),
    MATCH_LEFT("match_leave"),
    MATCH_TURN("match_turn"),
    MATCH_WON("match_won"),
    HEARTBEAT("heartbeat"),
    CONNECT("connect")
}