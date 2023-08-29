package com.connectly.messengerreviewbot.controllers.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class IncomingMessageEvent(
    @JsonProperty("object")
    val obj: String,
    val entry: List<Entry>
)

data class Entry(
    val id: String,
    val time: Instant,
    val messaging: List<Messaging>
)

data class Messaging(
    val sender: Sender,
    val recipient: Recipient,
    val timestamp: Instant,
    val message: Message
)

data class Sender(
    val id: String
)

data class Recipient(
    val id: String
)

data class Message(
    val mid: String,
    val text: String
)
