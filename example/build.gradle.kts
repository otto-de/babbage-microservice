plugins {
	id("babbage.conventions")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":core"))
}

base.archivesName.set("example")