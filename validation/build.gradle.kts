plugins {
    id("babbage.conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.validation:jakarta.validation-api")
}

base.archivesName.set("validation")