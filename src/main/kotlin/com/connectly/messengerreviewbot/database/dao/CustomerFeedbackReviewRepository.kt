package com.connectly.messengerreviewbot.database.dao

import com.connectly.messengerreviewbot.database.models.CustomerFeedbackReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerFeedbackReviewRepository: JpaRepository<CustomerFeedbackReview, Long> {
}
