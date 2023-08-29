package com.connectly.messengerreviewbot

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.codec.Hex
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor

@SpringBootApplication
class MessengerReviewBotApplication() {
	@Bean
	fun textEncryptor(
		@Value("\${spring.security.crypto.secret-key}") secretKey: String,
		@Value("\${spring.security.crypto.salt}") salt: String
	): TextEncryptor {
		return Encryptors.text(secretKey, salt)
	}
}

fun main(args: Array<String>) {
	runApplication<MessengerReviewBotApplication>(*args)
}
