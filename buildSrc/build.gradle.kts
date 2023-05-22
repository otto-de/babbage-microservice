plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")              // kotlin("jvm")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.8.10")                    // kotlin("plugin.spring")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.1.0")      // id("org.springframework.boot")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")           // id("io.spring.dependency-management")
    implementation("com.adarshr:gradle-test-logger-plugin:3.2.0")                   // id("com.adarshr.test-logger")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")       // id("io.gitlab.arturbosch.detekt")
}
