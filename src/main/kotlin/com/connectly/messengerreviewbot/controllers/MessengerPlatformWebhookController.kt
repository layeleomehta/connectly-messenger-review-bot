package com.connectly.messengerreviewbot.controllers

import com.connectly.messengerreviewbot.controllers.models.IncomingMessageEvent
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/v1/messenger-platform-webhook")
class MessengerPlatformWebhookController() {

    @PostMapping
    fun receiveMessengerPlatformEvent(@RequestBody body: IncomingMessageEvent): ResponseEntity<Any> {
        println(body)
        return ResponseEntity.ok().build()
    }

}
