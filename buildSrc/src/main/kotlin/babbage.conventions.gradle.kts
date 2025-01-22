import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("maven-publish")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.gitlab.arturbosch.detekt")

    id("com.adarshr.test-logger") // for nice formatting in console: https://github.com/radarsh/gradle-test-logger-plugin
}

group = "de.otto.babbage"
version = "0.5.2-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    /**
     * Only specify dependencies here that are necessary for all modules.
     * These do not have to be added again in the build.gradle.kts of the module.
     */
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.4.RELEASE")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    val coroutinesVersion = "1.6.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    val koTestVersion = "5.9.1"
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")

    val mockkVersion = "1.13.16"
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.mockk:mockk-jvm:$mockkVersion")

    /**
     * Specify versions here for dependencies that are not used by every module.
     * The dependency have to be configured in the module build.gradle.kts without a version.
     */
    constraints {
        val awsSdkVersion = "2.30.2"
        api("software.amazon.awssdk:auth:$awsSdkVersion")
        api("software.amazon.awssdk:s3:$awsSdkVersion")
        api("software.amazon.awssdk:ssm:$awsSdkVersion")

        // test dependencies
        api("com.ninja-squad:springmockk:4.0.2")
        api("org.jsoup:jsoup:1.18.3")
    }

}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        allWarningsAsErrors.set(true)
    }
}

detekt {
    baseline = file("../detekt-baseline.xml")
    config.setFrom(files("../detekt-config.yml"))
    buildUponDefaultConfig = true
}

/**
 * Tests
 */

tasks.named<Test>("test") {
    useJUnitPlatform()
}

testlogger {
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
}

/**
 * Publishing
 */

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

publishing {
    if (!base.archivesName.get().contains("example")) {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/otto-de/babbage-microservice")
                credentials {
                    username = (findProperty("github_username") as String?) ?: System.getenv("GITHUB_USERNAME")
                    password = (findProperty("github_token") as String?) ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }

        publications {
            create<MavenPublication>(base.archivesName.get()) {
                from(components["java"])
                artifact(tasks["sourcesJar"])
            }
        }
    }
}

