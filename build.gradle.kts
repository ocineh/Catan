plugins {
    java
    application
}

application {
    mainClass.set("catan.Catan");
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}