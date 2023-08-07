plugins {
    id("babbage.conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
}

base.archivesName.set("validation")