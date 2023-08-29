package com.connectly.messengerreviewbot.services

import com.connectly.messengerreviewbot.database.dao.BusinessPageRepository
import com.connectly.messengerreviewbot.database.models.BusinessPage
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Service

@Service
class BusinessPageService(
    private val businessPageRepository: BusinessPageRepository,
    private val textEncryptor: TextEncryptor,
    private val idGenerator: IdGenerator
) {

    fun registerBusinessPage(businessName: String, pageAccessToken: String, pageId: String): BusinessPage {
        getBusinessPageByPageId(pageId)?.let {
            throw Exception("A page with this page ID already exists!")
        }

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

    private fun getBusinessPageByPageId(pageId: String): BusinessPage? {
        return businessPageRepository.findByPageId(pageId)
    }

}
