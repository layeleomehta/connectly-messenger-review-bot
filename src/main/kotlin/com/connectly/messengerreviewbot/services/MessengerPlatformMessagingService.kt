package com.connectly.messengerreviewbot.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MessengerPlatformMessagingService(
    @Value("\${connectly.messenger-platform.send-api.base-url}")
    private val messengerPlatformSendApiBaseUrl: String,
    @Value("\${connectly.messenger-platform.send-api.version}")
    private val messengerPlatformSendApiVersion: String
    ) {

    fun sendCustomerFeedbackMessage() {

    }


}
