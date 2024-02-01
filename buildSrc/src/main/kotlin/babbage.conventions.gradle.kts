import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
version = "0.2.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    /**
     * Only specify dependencies here that are necessary for all modules.
     * These do not have to be added again in the build.gradle.kts of the module.
     */
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")

    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("io.mockk:mockk-jvm:1.13.4")

    /**
     * Specify versions here for dependencies that are not used by every module.
     * The dependency have to be configured in the module build.gradle.kts without a version.
     */
    constraints {
        api("software.amazon.awssdk:auth:2.20.7")
        api("software.amazon.awssdk:s3:2.20.7")
        api("software.amazon.awssdk:ssm:2.20.7")

        // test dependencies
        api("com.ninja-squad:springmockk:4.0.0")
        api("org.jsoup:jsoup:1.15.3")
        api("io.kotest.extensions:kotest-assertions-jsoup:1.0.0")
    }

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        allWarningsAsErrors = true
    }
}

detekt {
    baseline = file("../detekt-baseline.xml")
    config = files("../detekt-config.yml")
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

/**
 * The generated MavenPom has two `dependencyManagement` sections, which is invalid. There is a workaround to fix this
 * issue:
 * https://github.com/spring-gradle-plugins/dependency-management-plugin/issues/257#issuecomment-895790557
 */
tasks.withType<GenerateMavenPom>().all {
    doLast {
        val file = File("$buildDir/publications/${base.archivesName.get()}/pom-default.xml")
        var text = file.readText()
        val regex =
            "(?s)(<dependencyManagement>.+?<dependencies>)(.+?)(</dependencies>.+?</dependencyManagement>)".toRegex()
        val matcher = regex.find(text)
        if (matcher != null) {
            text = regex.replaceFirst(text, "")
            val firstDeps = matcher.groups[2]!!.value
            text = regex.replaceFirst(text, "$1$2$firstDeps$3")
        }
        file.writeText(text)
    }
}

