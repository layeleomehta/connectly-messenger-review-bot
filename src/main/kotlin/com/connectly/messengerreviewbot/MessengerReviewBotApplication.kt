package com.connectly.messengerreviewbot

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.codec.Hex
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class MessengerReviewBotApplication() {
	@Bean
	fun textEncryptor(
		@Value("\${spring.security.crypto.secret-key}") secretKey: String,
		@Value("\${spring.security.crypto.salt}") salt: String
	): TextEncryptor {
		return Encryptors.text(secretKey, salt)
	}

	@Bean
	fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
		return builder.build()
	}

}

fun main(args: Array<String>) {
	runApplication<MessengerReviewBotApplication>(*args)
}
