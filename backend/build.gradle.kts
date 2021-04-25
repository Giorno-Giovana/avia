description = " BACKEND "

plugins {
    val kotlinVersion = "1.5.0-RC"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

repositories { mavenCentral(); mavenLocal(); maven("https://dl.bintray.com/kotlin/kotlin-eap"); maven("https://jitpack.io") }

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")
    val openapi = "1.5.7"
    implementation("org.springdoc:springdoc-openapi-ui:$openapi")
    implementation("org.springdoc:springdoc-openapi-kotlin:$openapi")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:$openapi")
    implementation("org.springdoc:springdoc-openapi-security:$openapi")
//    implementation("org.postgresql", "postgresql")
    runtimeOnly("com.h2database", "h2")
    implementation("org.liquibase", "liquibase-core")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.12.3")
    val coroutines = "1.4.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    implementation("commons-io:commons-io:2.8.0")
    implementation("eu.bitwalker:UserAgentUtils:1.21")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.4")
    implementation("org.springframework.security", "spring-security-oauth2-client")
    implementation("org.springframework.boot", "spring-boot-starter-security")
    implementation("org.springframework.security", "spring-security-oauth2-jose")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    val caffeineCache = "3.0.1"
    implementation("org.springframework.boot", "spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeineCache")
    implementation("com.github.ben-manes.caffeine:jcache:$caffeineCache")
    annotationProcessor("org.springframework.boot", "spring-boot-starter-actuator")
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
}

tasks {
    defaultTasks("deploy")
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions { jvmTarget = "16" } }
    test { useJUnitPlatform() }
}
