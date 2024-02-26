plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")              // kotlin("jvm")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.9.22")                    // kotlin("plugin.spring")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.2.3")      // id("org.springframework.boot")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.4")           // id("io.spring.dependency-management")
    implementation("com.adarshr:gradle-test-logger-plugin:4.0.0")                   // id("com.adarshr.test-logger")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.5")       // id("io.gitlab.arturbosch.detekt")
}
