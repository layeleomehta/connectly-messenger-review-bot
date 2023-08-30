package com.connectly.messengerreviewbot.controllers

import com.connectly.messengerreviewbot.controllers.models.IncomingMessageEvent
import com.connectly.messengerreviewbot.services.BusinessPageService
import com.connectly.messengerreviewbot.services.CustomerFeedbackReviewService
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
    private val businessPageService: BusinessPageService,
    private val customerFeedbackReviewService: CustomerFeedbackReviewService
) {

    @PostMapping
    fun receiveMessengerPlatformWebhookEvent(@RequestBody body: IncomingMessageEvent): ResponseEntity<Any> {
        body.entry.forEach { entry ->
            entry.messaging.forEach { messaging ->
                if(messaging.message != null) {
                    println("this is a text message")
                }

                if(messaging.messagingFeedback != null) {
                    // obtain the business page by recipient id (business's page id)
                    val businessPageId = messaging.recipient.id
                    businessPageService.getBusinessPageByPageId(businessPageId)?.let { businessPage ->
                        // save the review
                        val starRating = messaging.messagingFeedback.feedbackScreens.first().questions.get(key = "hauydmns8")?.payload!!.toInt()
                        val reviewText = messaging.messagingFeedback.feedbackScreens.first().questions.get(key = "hauydmns8")?.followUp?.payload

                        customerFeedbackReviewService.createCustomerFeedbackReview(businessPage, reviewText, starRating)
                    } ?: throw Exception("Unable to find this business page by the business page id")
                }
            }
        }

        println(body)
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

}
