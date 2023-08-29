package com.connectly.messengerreviewbot.services

import org.springframework.stereotype.Service
import java.util.*

@Service
class IdGenerator {
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}
