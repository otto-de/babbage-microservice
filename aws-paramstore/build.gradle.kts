plugins {
    id("babbage.conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":aws-auth"))

    implementation("software.amazon.awssdk:ssm")
}

base.archivesName.set("aws-paramstore")