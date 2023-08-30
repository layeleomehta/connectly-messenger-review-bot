package com.connectly.messengerreviewbot.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MessengerPlatformMessagingService(
    private val restTemplate: RestTemplate,
    @Value("\${connectly.messenger-platform.send-api.base-url}")
    private val messengerPlatformSendApiBaseUrl: String,
    @Value("\${connectly.messenger-platform.send-api.version}")
    private val messengerPlatformSendApiVersion: String
    ) {

    fun sendCustomerFeedbackMessage(recipientPsid: String) {
    }


}
