package com.connectly.messengerreviewbot.services.models

import com.fasterxml.jackson.annotation.JsonProperty


data class CreateCustomerFeedbackRequestBody(
    val recipient: Recipient,
    val message: Message
)

data class Recipient(
    val id: String
)

data class Message(
    val attachment: Attachment
)

data class Attachment(
    val type: String = "template",
    val payload: Payload
)

data class Payload(
    @JsonProperty("template_type")
    val templateType: String = "customer_feedback",
    val title: String,
    val subtitle: String,
    @JsonProperty("button_title")
    val buttonTitle: String = "Rate Your Experience",
    @JsonProperty("feedback_screens")
    val feedbackScreens: List<FeedbackScreen>,
    @JsonProperty("business_privacy")
    val businessPrivacy: BusinessPrivacy,
    @JsonProperty("expires_in_days")
    val expiresInDays: Int? = null
)

data class FeedbackScreen(
    val questions: List<Question>
)

data class Question(
    val id: String = "review-question-4dea3176-33c0-48e7-8db4-6c02e5ff92c8",
    val type: String = "csat",
    val title: String,
    @JsonProperty("score_label")
    val scoreLabel: String = "neg_pos",
    @JsonProperty("score_option")
    val scoreOption: String = "five_stars",
    val followUp: FollowUp
)

data class FollowUp(
    val type: String = "free_form",
    val placeholder: String? = "Give additional feedback"
)

data class BusinessPrivacy(
    val url: String
)


/*
{
  "recipient": {
    "id": "6456535891062838"
  },
  "message": {
    "attachment": {
      "type": "template",
      "payload": {
        "template_type": "customer_feedback",
        "title": "Rate your experience with Original Coast Clothing.",
        "subtitle": "Let Original Coast Clothing know how they are doing by answering two questions",
        "button_title": "Rate Experience",
        "feedback_screens": [{
          "questions":[{
            "id": "hauydmns8", // Unique id for question that business sets
            "type": "csat",
            "title": "How would you rate your experience with Original Coast Clothing?", // Optional. If business does not define, we show standard text. Standard text based on question type ("csat", "nps", "ces" >>> "text")
            "score_label": "neg_pos", // Optional
            "score_option": "five_stars", // Optional
            "follow_up": // Optional. Inherits the title and id from the previous question on the same page.  Only free-from input is allowed. No other title will show.
            {
              "type": "free_form",
              "placeholder": "Give additional feedback" // Optional
            }
          }]
        }],
        "business_privacy":
        {
            "url": "https://www.example.com"
         },
        "expires_in_days" : 3 // Optional, default 1 day, business defines 1-7 days
      }
    }
  }
}
* */
