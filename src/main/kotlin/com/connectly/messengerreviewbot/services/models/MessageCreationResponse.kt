package com.connectly.messengerreviewbot.services.models

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageCreationResponse(
    @JsonProperty("recipient_id")
    val recipientId: String,
    @JsonProperty("message_id")
    val messageId: String
)
