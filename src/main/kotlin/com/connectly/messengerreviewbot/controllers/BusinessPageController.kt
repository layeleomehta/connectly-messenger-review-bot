package com.connectly.messengerreviewbot.controllers

import com.connectly.messengerreviewbot.controllers.models.BusinessPageResponse
import com.connectly.messengerreviewbot.controllers.models.RegisterBusinessPageBody
import com.connectly.messengerreviewbot.services.BusinessPageService
import com.connectly.messengerreviewbot.services.MessengerPlatformMessagingService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/v1/business-page")
class BusinessPageController(
    private val businessPageService: BusinessPageService,
    private val messengerPlatformMessagingService: MessengerPlatformMessagingService
) {
    // TODO: test controller; please delete
    @GetMapping
    fun hi(): ResponseEntity<Any> {
        businessPageService.getBusinessPageByPageId("112810605251603")?.let {
            messengerPlatformMessagingService.sendCustomerFeedbackMessage(
                recipientPsid= "6456535891062838", businessPage = it
            )
        }

        return ResponseEntity.ok().build()
    }

    @PostMapping
    fun registerBusinessPage(@RequestBody body: RegisterBusinessPageBody): ResponseEntity<Any> {
        businessPageService.registerBusinessPage(
            businessName = body.businessName,
            pageAccessToken = body.pageAccessToken,
            pageId = body.pageId
        ).let {businessPage ->
            return ResponseEntity.ok(BusinessPageResponse(businessPage))
        }
    }

}
