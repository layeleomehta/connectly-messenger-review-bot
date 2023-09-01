package com.connectly.messengerreviewbot.controllers.models

import com.fasterxml.jackson.annotation.JsonProperty

data class IncomingTextMessage(
    @JsonProperty("quick_reply")
    val quickReply: QuickReply?,
    val mid: String,
    val text: String,
    val nlp: Nlp?
)

data class Nlp(
    val traits: Trait
)

data class Trait(
    @JsonProperty("wit\$greetings")
    val greeting: List<TraitValue>?,
    @JsonProperty("wit\$bye")
    val bye: List<TraitValue>?
)

data class TraitValue(
    val value: Boolean,
    val confidence: Double
)

data class QuickReply(
    val payload: String
)
