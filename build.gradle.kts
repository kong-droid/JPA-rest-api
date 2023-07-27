val NEXUS_CENTRAL_URL : String by project
val NEXUS_SNAPSHOT_URL : String by project
val NEXUS_USERNAME : String by project
val NEXUS_PASSWORD : String by project

plugins {
    java
    id ("org.springframework.boot") version "2.7.11"
    id ("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group= "shop.board-pilot"
version= "0.0.1-SNAPSHOT"

java {
    sourceCompatibility= JavaVersion.VERSION_11
    targetCompatibility= JavaVersion.VERSION_11
}

repositories {
    maven {
        url = uri(NEXUS_CENTRAL_URL)
        isAllowInsecureProtocol = true
        credentials {
            username= NEXUS_USERNAME
            password= NEXUS_PASSWORD
        }
    }

    maven {
        url = uri(NEXUS_SNAPSHOT_URL)
        isAllowInsecureProtocol = true
        credentials {
            username= NEXUS_USERNAME
            password= NEXUS_PASSWORD
        }
    }
}

dependencies {
    implementation("site.kongdroid:common:0.0.1-SNAPSHOT")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("commons-codec:commons-codec:1.15")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

