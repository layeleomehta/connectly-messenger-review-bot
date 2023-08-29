package com.connectly.messengerreviewbot.services

import com.connectly.messengerreviewbot.database.dao.BusinessPageRepository
import com.connectly.messengerreviewbot.database.models.BusinessPage
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Service

@Service
class BusinessPageService(
    private val businessPageRepository: BusinessPageRepository,
    private val textEncryptor: TextEncryptor
) {

    fun registerBusinessPage(businessName: String, pageAccessToken: String, pageId: String): BusinessPage? {
        return null
    }

}
