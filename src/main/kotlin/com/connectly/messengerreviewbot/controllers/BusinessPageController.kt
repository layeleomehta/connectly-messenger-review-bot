package com.connectly.messengerreviewbot.controllers

import com.connectly.messengerreviewbot.controllers.models.RegisterBusinessPageBody
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/v1/business-page")
class BusinessPageController() {

    @PostMapping
    fun registerBusinessPage(body: RegisterBusinessPageBody): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }

}
