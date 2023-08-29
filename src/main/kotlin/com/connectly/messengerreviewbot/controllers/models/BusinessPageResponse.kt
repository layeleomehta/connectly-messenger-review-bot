package com.connectly.messengerreviewbot.controllers.models

import com.connectly.messengerreviewbot.database.models.BusinessPage

data class BusinessPageResponse(
    val businessName: String
)  {
    constructor(businessPage: BusinessPage) : this(
        businessPage.businessName
    )
}
