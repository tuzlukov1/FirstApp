import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "me.tyzlu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.testng:testng:7.1.0")
    implementation("io.rest-assured:rest-assured:4.4.0")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.eclipse.jetty:jetty-server:9.4.0.M0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    implementation(kotlin("stdlib"))
}

tasks.test {
    useTestNG()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}