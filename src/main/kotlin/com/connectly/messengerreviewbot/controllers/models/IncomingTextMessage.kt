package com.connectly.messengerreviewbot.controllers.models

import com.fasterxml.jackson.annotation.JsonProperty

data class IncomingTextMessage(
    @JsonProperty("quick_reply")
    val quickReply: QuickReply?,
    val mid: String,
    val text: String
)

data class QuickReply(
    val payload: String
)
