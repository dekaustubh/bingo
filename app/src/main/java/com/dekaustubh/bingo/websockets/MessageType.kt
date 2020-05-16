package com.dekaustubh.bingo.websockets

enum class MessageType(val value: String) {
    JOIN("join"),
    START("start"),
    LEAVE("leave"),
    TAKE_TURN("take_turn"),
    UPDATE("update"),
    WIN("win"),
    HEARTBEAT("heartbeat"),
    CONNECT("connect")
}