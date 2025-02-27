plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    val kotlinVersion = "2.0.10"
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")              // kotlin("jvm")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")                    // kotlin("plugin.spring")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.4.3")      // id("org.springframework.boot")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.7")           // id("io.spring.dependency-management")
    implementation("com.adarshr:gradle-test-logger-plugin:4.0.0")                   // id("com.adarshr.test-logger")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.7")       // id("io.gitlab.arturbosch.detekt")
}
