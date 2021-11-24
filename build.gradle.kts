plugins {
    java
    application
}

application {
    mainClass.set("catan.Game");
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}