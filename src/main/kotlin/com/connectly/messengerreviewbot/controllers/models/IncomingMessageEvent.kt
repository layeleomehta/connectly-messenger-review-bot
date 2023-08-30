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
    val message: Message?,
    @JsonProperty("messaging_feedback")
    val messagingFeedback: MessagingFeedback?
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

data class MessagingFeedback(
    @JsonProperty("feedback_screens")
    val feedbackScreens: List<FeedbackScreen>
)

data class FeedbackScreen(
    val screenId: Int,
    val questions: Map<String, QuestionDetails>
)

data class QuestionDetails(
    val type: String,
    val payload: String,
    @JsonProperty("follow_up")
    val followUp: FollowUp
)

data class FollowUp(
    val type: String,
    val payload: String
)

