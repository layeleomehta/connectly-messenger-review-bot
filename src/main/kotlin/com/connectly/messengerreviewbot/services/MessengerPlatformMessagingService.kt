package com.connectly.messengerreviewbot.services

import com.connectly.messengerreviewbot.database.models.BusinessPage
import com.connectly.messengerreviewbot.services.models.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate

@Service
class MessengerPlatformMessagingService(
    private val restTemplate: RestTemplate,
    @Value("\${connectly.messenger-platform.send-api.base-url}")
    private val messengerPlatformSendApiBaseUrl: String,
    @Value("\${connectly.messenger-platform.send-api.version}")
    private val messengerPlatformSendApiVersion: String,
    @Value("\${connectly.messenger-platform.send-api.review-question-id}")
    private val messengerPlatformReviewQuestionId: String,
    private val textEncryptor: TextEncryptor
) {

    fun sendCustomerFeedbackMessage(recipientPsid: String, businessPage: BusinessPage): MessageCreationResponse? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestBody = mapOf(
            "recipient" to mapOf("id" to recipientPsid),
            "message" to mapOf(
                "attachment" to mapOf(
                    "type" to "template",
                    "payload" to mapOf(
                        "template_type" to "customer_feedback",
                        "title" to "Rate your experience with ${businessPage.businessName}.",
                        "subtitle" to "Let ${businessPage.businessName} know how they are doing by answering two questions",
                        "button_title" to "Rate Experience",
                        "feedback_screens" to listOf(
                            mapOf(
                                "questions" to listOf(
                                    mapOf(
                                        "id" to messengerPlatformReviewQuestionId,
                                        "type" to "csat",
                                        "title" to "How would you rate your experience with ${businessPage.businessName}",
                                        "score_label" to "neg_pos",
                                        "score_option" to "five_stars",
                                        "follow_up" to mapOf(
                                            "type" to "free_form",
                                            "placeholder" to "Give additional feedback"
                                        )
                                    )
                                )
                            )
                        ),
                        "business_privacy" to mapOf("url" to "https://www.example.com"),
                        "expires_in_days" to 3
                    )
                )
            )
        )

        val requestEntity = HttpEntity(requestBody, headers)

        return try {
            restTemplate.exchange(
                "$messengerPlatformSendApiBaseUrl/$messengerPlatformSendApiVersion/${businessPage.pageId}/messages?access_token=${textEncryptor.decrypt(businessPage.hashedPageAccessToken)}",
                HttpMethod.POST,
                requestEntity,
                MessageCreationResponse::class.java
            ).body ?: throw Exception("Cannot create message due to an unknown error")
        } catch (e: ResourceAccessException) {
            throw Exception("Error connecting to Send API")
        } catch (e: HttpClientErrorException) {
            throw Exception("Cannot create message due to invalid inputs")
        }
    }

}
