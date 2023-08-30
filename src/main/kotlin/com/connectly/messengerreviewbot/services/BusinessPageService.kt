package com.connectly.messengerreviewbot.services

import com.connectly.messengerreviewbot.database.dao.BusinessPageRepository
import com.connectly.messengerreviewbot.database.models.BusinessPage
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
class BusinessPageService(
    private val businessPageRepository: BusinessPageRepository,
    private val textEncryptor: TextEncryptor,
    private val idGenerator: IdGenerator,
    @Value("\${connectly.messenger-platform.messenger-profile-api.base-url}")
    private val messengerPlatformProfileApiBaseUrl: String,
    @Value("\${connectly.messenger-platform.send-api.postback.get-started-payload}")
    private val messengerPlatformPostbackGetStartedPayload: String,
    @Value("\${connectly.messenger-platform.send-api.postback.persistent-menu-payload}")
    private val messengerPlatformPostbackPersistentMenuPayload: String,
    @Value("\${connectly.messenger-platform.send-api.base-url}")
    private val messengerPlatformSendApiBaseUrl: String,
    @Value("\${connectly.messenger-platform.send-api.version}")
    private val messengerPlatformSendApiVersion: String,
    private val restTemplate: RestTemplate
    ) {

    fun registerBusinessPage(businessName: String, pageAccessToken: String, pageId: String): BusinessPage {
        getBusinessPageByPageId(pageId)?.let {
            throw Exception("A page with this page ID already exists!")
        }

        // register get started button and persistent menu.
        registerGetStartedButton(pageAccessToken)
        registerPersistentMenu(pageAccessToken)

        val hashedPageAccessToken = textEncryptor.encrypt(pageAccessToken)
        return businessPageRepository.save(
            BusinessPage(
                id = null,
                externalId = idGenerator.generateId(),
                businessName = businessName,
                hashedPageAccessToken = hashedPageAccessToken,
                pageId = pageId
            )
        )
    }

    fun getBusinessPageByPageId(pageId: String): BusinessPage? {
        return businessPageRepository.findByPageId(pageId)
    }

    private fun registerGetStartedButton(pageAccessToken: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestBody = mapOf(
            "get_started" to mapOf("payload" to messengerPlatformPostbackGetStartedPayload)
        )

        val requestEntity = HttpEntity(requestBody, headers)
        try {
            restTemplate.exchange(
                "$messengerPlatformProfileApiBaseUrl/messenger_profile?access_token=$pageAccessToken",
                HttpMethod.POST,
                requestEntity,
                Void::class.java
            )
        } catch (e: ResourceAccessException) {
            throw Exception("Error connecting to Profile API")
        } catch (e: HttpClientErrorException) {
            throw Exception("Cannot register a Get Started button due to invalid inputs")
        }
    }

    private fun registerPersistentMenu(pageAccessToken: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestBody = mapOf(
            "persistent_menu" to listOf(
                mapOf(
                    "locale" to "default",
                    "composer_input_disabled" to false,
                    "call_to_actions" to listOf(
                        mapOf(
                            "type" to "postback",
                            "title" to "Leave us a review!",
                            "payload" to messengerPlatformPostbackPersistentMenuPayload
                        )
                    )
                )
            )
        )

        val requestEntity = HttpEntity(requestBody, headers)
        try {
            restTemplate.exchange(
                "$messengerPlatformSendApiBaseUrl/$messengerPlatformSendApiVersion/me/messenger_profile?access_token=$pageAccessToken",
                HttpMethod.POST,
                requestEntity,
                Void::class.java
            )
        } catch (e: ResourceAccessException) {
            throw Exception("Error connecting to Profile API")
        } catch (e: HttpClientErrorException) {
            throw Exception("Cannot register a Persistent Menu due to invalid inputs")
        }
    }

}
