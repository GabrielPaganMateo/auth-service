-- Local Development

Prerequisites

1. Generate keystore in resources/ directory

2. Setup SSL/TLS in application-local.yml file within resources/ directory

Startup

1. mvn clean package spring-boot:repackage

2. java -jar target/auth-service-1.0.jar --spring.profiles.active=local