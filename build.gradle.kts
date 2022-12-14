import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    id("io.qameta.allure") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.22")
    testImplementation("org.slf4j:slf4j-simple:2.0.5")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")
    //Cucumber tests
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.junit.platform:junit-platform-suite:1.9.0")
    testImplementation("io.cucumber:cucumber-java8:7.8.1")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.8.1")
}

tasks.test {
    //useJUnitPlatform()
    useTestNG()
    finalizedBy("allureReport")
}

allure {
    report {
        version.set("2.20.0")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}