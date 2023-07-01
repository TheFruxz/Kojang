plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    `maven-publish`
}

group = "de.fruxz.kojang"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {

    api("com.github.TheFruxz:Ascend:2023.2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("io.ktor:ktor-client-core-jvm:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        mavenLocal()
    }

    publications.create("Kojang", MavenPublication::class) {

        from(components["kotlin"])

        artifactId = "kojang"
        version = version.lowercase()

    }
}


kotlin {
    jvmToolchain(17)
    explicitApiWarning() // This project is a library, so we want to be as explicit as possible
}