package com.connectly.messengerreviewbot.controllers.models

import com.fasterxml.jackson.annotation.JsonProperty

data class IncomingTextMessage(
    @JsonProperty("quick_reply")
    val quickReply: QuickReply?,
    val mid: String,
    val text: String,
    val nlp: NlpDetails
)

data class NlpDetails(
    val traits: Trait
)

data class Trait(
    @JsonProperty("wit\$greetings")
    val greeting: List<TraitValues>?,
    @JsonProperty("wit\$bye")
    val bye: List<TraitValues>?
)

data class TraitValues(
    val value: Boolean,
    val confidence: Double
)

data class QuickReply(
    val payload: String
)
