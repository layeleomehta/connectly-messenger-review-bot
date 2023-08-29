package com.connectly.messengerreviewbot.database.dao

import com.connectly.messengerreviewbot.database.models.BusinessPage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BusinessPageRepository: JpaRepository<BusinessPage, Long> {
    fun findByPageId(pageId: String): BusinessPage?
}
