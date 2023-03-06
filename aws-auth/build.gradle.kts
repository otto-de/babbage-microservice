plugins {
    id("babbage.conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("software.amazon.awssdk:auth")
}

base.archivesName.set("aws-auth")