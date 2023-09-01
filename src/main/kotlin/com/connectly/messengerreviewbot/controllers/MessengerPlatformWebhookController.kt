package com.connectly.messengerreviewbot.controllers

import com.connectly.messengerreviewbot.controllers.models.IncomingMessageEvent
import com.connectly.messengerreviewbot.services.BusinessPageService
import com.connectly.messengerreviewbot.services.CustomerFeedbackReviewService
import com.connectly.messengerreviewbot.services.MessengerPlatformMessagingService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/api/v1/messenger-platform-webhook")
class MessengerPlatformWebhookController(
    @Value("\${connectly.messenger-platform.webhook.verify-token}")
    private val messengerPlatformWebhookVerifyToken: String,
    @Value("\${connectly.messenger-platform.send-api.review-question-id}")
    private val messengerPlatformReviewQuestionId: String,
    @Value("\${connectly.messenger-platform.send-api.postback.get-started-payload}")
    private val messengerPlatformPostbackGetStartedPayload: String,
    @Value("\${connectly.messenger-platform.send-api.postback.persistent-menu-payload}")
    private val messengerPlatformPostbackPersistentMenuPayload: String,
    @Value("\${connectly.messenger-platform.send-api.postback.quick-replies-payload}")
    private val messengerPlatformPostbackQuickRepliesPayload: String,
    private val businessPageService: BusinessPageService,
    private val customerFeedbackReviewService: CustomerFeedbackReviewService,
    private val messengerPlatformMessagingService: MessengerPlatformMessagingService
) {
    private val logger = LoggerFactory.getLogger(MessengerPlatformWebhookController::class.java)

    @PostMapping
    fun receiveMessengerPlatformWebhookEvent(@RequestBody body: IncomingMessageEvent): ResponseEntity<Any> {
        body.entry.forEach { entry ->
            entry.messaging.forEach { messaging ->
                // obtain the business page by recipient id (business's page id)
                val businessPageId = messaging.recipient.id
                val businessPage = businessPageService.getBusinessPageByPageId(businessPageId)
                    ?: throw Exception("Unable to find this business page by the business page id")

                if(messaging.message != null) {
                    if(messaging.message.quickReply != null && messaging.message.quickReply.payload == messengerPlatformPostbackQuickRepliesPayload) {
                        // this is a quick reply; respond by requesting a customer review
                        messengerPlatformMessagingService.sendCustomerFeedbackRequestTemplate(
                            recipientPsid = messaging.sender.id,
                            businessPage = businessPage
                        )
                    } else {
                        // this is a text message.
                        messaging.message.nlp.traits.greeting?.first()?.let { traitValues ->
                            if(traitValues.value && traitValues.confidence > 0.6) {
                                // the customer has sent greeting, send back a greeting text message
                                messengerPlatformMessagingService.sendPlainTextMessage(
                                    recipientPsid = messaging.sender.id,
                                    businessPage = businessPage,
                                    messageText = "Hey it's so nice to meet you! Please leave us a review if you can."
                                )
                            }
                        }

                        messaging.message.nlp.traits.bye?.first()?.let { traitValues ->
                            if(traitValues.value && traitValues.confidence > 0.6) {
                                // the customer has sent a farewell, send back a bye text message
                                messengerPlatformMessagingService.sendPlainTextMessage(
                                    recipientPsid = messaging.sender.id,
                                    businessPage = businessPage,
                                    messageText = "Bye and come back soon! Please leave us a review if you can."
                                )
                            }
                        }

                        // for all other text messages respond by requesting a quick reply for customer feedback
                        messengerPlatformMessagingService.sendCustomerFeedbackRequestQuickReplyMessage(
                            recipientPsid = messaging.sender.id,
                            businessPage = businessPage
                        )
                    }
                }

                if(messaging.messagingFeedback != null) {
                    // process and save a customer's review
                    val starRating = messaging.messagingFeedback.feedbackScreens.first().questions.get(key = messengerPlatformReviewQuestionId)?.payload!!.toInt()
                    val reviewText = messaging.messagingFeedback.feedbackScreens.first().questions.get(key = messengerPlatformReviewQuestionId)?.followUp?.payload

                    customerFeedbackReviewService.createCustomerFeedbackReview(businessPage, reviewText, starRating).let {
                        logger.debug("successfully saved customer review into the CustomerFeedbackReview table.")
                    }
                }

                if(messaging.postback != null) {
                    // process a message postback and request for a customer feedback review:
                    if(messaging.postback.payload == messengerPlatformPostbackPersistentMenuPayload
                        || messaging.postback.payload == messengerPlatformPostbackGetStartedPayload
                    ) {
                        messengerPlatformMessagingService.sendCustomerFeedbackRequestTemplate(
                            recipientPsid = messaging.sender.id,
                            businessPage = businessPage
                        )
                    }
                }
            }
        }

        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun messengerPlatformWebhookVerificationRequest(
        @RequestParam("hub.mode") hubMode: String?,
        @RequestParam("hub.verify_token") verifyToken: String?,
        @RequestParam("hub.challenge") challenge: String?
    ): ResponseEntity<Any> {
        if(hubMode != null && verifyToken != null) {
            if(hubMode == "subscribe" && verifyToken == messengerPlatformWebhookVerifyToken) return ResponseEntity.ok(challenge)
        }
        return ResponseEntity.ok().build()
    }

    private fun handleIncomingTextMessage() {}

    private fun handleIncomingCustomerFeedbackReview() {}

    private fun handleIncomingPersistentMenuResponse() {}

}
