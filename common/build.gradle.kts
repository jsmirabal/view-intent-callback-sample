plugins {
    id("java-library")
    id("kotlin")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api("org.junit.jupiter:junit-jupiter:5.8.2")
    api("io.mockk:mockk:1.10.6")
}