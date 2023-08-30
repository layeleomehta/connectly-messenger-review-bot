package com.connectly.messengerreviewbot.controllers.models

import com.fasterxml.jackson.annotation.JsonProperty

data class IncomingCustomerFeedbackMessage(
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
