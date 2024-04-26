plugins {
	id("babbage.conventions")

	id("com.gorylenko.gradle-git-properties") version "2.4.1"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":core"))
}

base.archivesName.set("example")