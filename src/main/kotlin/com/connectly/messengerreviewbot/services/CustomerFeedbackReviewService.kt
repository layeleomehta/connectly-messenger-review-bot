package com.connectly.messengerreviewbot.services

import com.connectly.messengerreviewbot.database.dao.CustomerFeedbackReviewRepository
import com.connectly.messengerreviewbot.database.models.BusinessPage
import com.connectly.messengerreviewbot.database.models.CustomerFeedbackReview
import org.springframework.stereotype.Service

@Service
class CustomerFeedbackReviewService(
    private val customerFeedbackReviewRepository: CustomerFeedbackReviewRepository,
    private val idGenerator: IdGenerator
) {

    fun createCustomerFeedbackReview(
        businessPage: BusinessPage,
        reviewText: String?,
        starRating: Int,
        productName: String?
    ): CustomerFeedbackReview {
        return customerFeedbackReviewRepository.save(
            CustomerFeedbackReview(
                id = null,
                externalId = idGenerator.generateId(),
                businessPage = businessPage,
                reviewText = reviewText,
                starRating = starRating,
                productName = productName
            )
        )
    }
}
