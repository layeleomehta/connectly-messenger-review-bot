import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}

	dependencies {
		classpath("com.google.cloud.tools:appengine-gradle-plugin:2.+")
	}
}

apply {
	plugin("com.google.cloud.tools.appengine")
}

plugins {
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
}

group = "com.connectly"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.security:spring-security-crypto:6.0.2")
	implementation("com.google.cloud.sql:postgres-socket-factory:1.11.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

configure<com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension> {
	stage {
		setArtifact("build/libs/messenger-review-bot-0.0.1-SNAPSHOT.jar")
	}

	deploy {
		projectId = "connectly-397506"
		version = "GCLOUD_CONFIG"
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
