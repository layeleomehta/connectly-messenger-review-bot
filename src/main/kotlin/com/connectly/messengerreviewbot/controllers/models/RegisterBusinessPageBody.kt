package com.connectly.messengerreviewbot.controllers.models

data class RegisterBusinessPageBody(
    val businessName: String,
    val pageAccessToken: String,
    val pageId: String,
)
