package com.connectly.messengerreviewbot.services

import com.connectly.messengerreviewbot.services.models.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
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
    private val messengerPlatformSendApiVersion: String
    ) {

    fun sendCustomerFeedbackMessage() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestBody = mapOf(
            "recipient" to mapOf("id" to "6456535891062838"),
            "message" to mapOf(
                "attachment" to mapOf(
                    "type" to "template",
                    "payload" to mapOf(
                        "template_type" to "customer_feedback",
                        "title" to "Rate your experience with Original Coast Clothing.",
                        "subtitle" to "Let Original Coast Clothing know how they are doing by answering two questions",
                        "button_title" to "Rate Experience",
                        "feedback_screens" to listOf(
                            mapOf(
                                "questions" to listOf(
                                    mapOf(
                                        "id" to "hauydmns8",
                                        "type" to "csat",
                                        "title" to "How would you rate your experience with Original Coast Clothing?",
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


        try {
            restTemplate.exchange(
                "https://graph.facebook.com/v17.0/112810605251603/messages?access_token=EAALqMlLg0vUBO2LkyzcMpDaV9sZBZBylK1ZBb4s9Jdngq5OXZAhhvGYZC11G8MvKPIEmxEDjBCgOxiYzZBO3aNcP9QGKSfL3VITjFP7NFOVpYFnZAVyX8vCrf7iagx5heLbTf14pVEz2o5AYqrwgjWrG7tZCx7ZBBBR9gyM0rcIr1fJYQWqB4SAKBMkDdHRZAH3BCo",
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
