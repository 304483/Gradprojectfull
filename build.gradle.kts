plugins {
    java
    id("io.qameta.allure") version "2.12.0"

}

group = "com.ust.sdet"
version = "0.1.0"

val seleniumVersion = "4.45.0"
val selenideVersion = "7.16.2"
val junitVersion = "5.13.4"
val cucumberVersion = "7.34.3"
val allureVersion = "2.33.0"
val extentVersion = "5.1.2"
val extentCucumberAdapterVersion = "1.14.0"
val slf4jVersion = "2.0.17"
val testcontainersVersion = "2.0.5"
val flywayVersion = "10.22.0"
val postgresqlVersion = "42.7.4"

java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}
allure {
    version.set("2.33.0")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-picocontainer")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("com.aventstack:extentreports:$extentVersion")
    testImplementation("tech.grasshopper:extentreports-cucumber7-adapter:$extentCucumberAdapterVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter:${testcontainersVersion}")

    testImplementation("org.flywaydb:flyway-core:$flywayVersion")
    testImplementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")
    testImplementation("org.postgresql:postgresql:${postgresqlVersion}")

    testImplementation("org.testcontainers:testcontainers-postgresql:${testcontainersVersion}")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(22)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty(
        "allure.results.directory",
        layout.buildDirectory.dir("allure-results").get().asFile.absolutePath
    )
    systemProperty("baseUrl", providers.gradleProperty("baseUrl").orElse("http://localhost:5173").get())
    systemProperty("headless", providers.gradleProperty("headless").orElse("false").get())
    systemProperty("browser", providers.gradleProperty("browser").orElse("chrome").get())
    systemProperty("build.label", providers.gradleProperty("buildLabel").orElse("gradle-local").get())
    systemProperty("cucumber.publish.quiet", "true")
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.SHORT
    }
}

fun Test.useProjectTestClasses() {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}

tasks.test {
    description = "Runs the main Selenium/JUnit regression tests."
    group = "verification"
    useJUnitPlatform()

    include("**/CatalogPOMTest.class", "**/Refactoring_Test.class")
    maxParallelForks = 1
}

val catalogPomTest by tasks.registering(Test::class) {
    description = "Runs the catalog page object model regression test."
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/CatalogPOMTest.class")
    maxParallelForks = 1
}

val refactoringTest by tasks.registering(Test::class) {
    description = "Runs the refactoring regression test."
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/Refactoring_Test.class")
    maxParallelForks = 1
}

val parallelStructureTest by tasks.registering(Test::class) {
    description = "Demonstrates Gradle test forks with no-browser checks."
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/Refactoring_Test.class")
    maxParallelForks = Runtime.getRuntime().availableProcessors().coerceAtMost(2)
}

val orderTest by tasks.registering(Test::class) {
    description = "Demonstrates Gradle test forks with no-browser checks."
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/OrderTest.class")
    maxParallelForks = 1
}

val cucumberSmoke by tasks.registering(Test::class) {
    description = "Runs Cucumber smoke scenarios through the Gradle JUnit Platform."
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/RunCucumberTest.class")
    systemProperty("cucumber.filter.tags", "@smoke")
    maxParallelForks = 1
}

tasks.register("projectBuildSummary") {
    description = "Prints the Gradle command map for this project."
    group = "help"
    doLast {
        println(
            """
            Project Build Summary
            Gradle compile: ./gradlew clean testClasses
            Gradle main tests: ./gradlew test
            Gradle catalog test: ./gradlew catalogPomTest
            Gradle refactoring test: ./gradlew refactoringTest
            Gradle smoke: ./gradlew cucumberSmoke -Pheadless=true
            """.trimIndent()
        )
    }
}
