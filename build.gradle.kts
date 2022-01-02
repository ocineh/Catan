plugins {
    java
    application
}

application {
    mainClassName = "catan.Catan";
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}