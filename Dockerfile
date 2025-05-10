FROM openjdk:17-jdk-slim

WORKDIR /app

# gradle 파일들 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# build
RUN chmod +x gradlew && ./gradlew build -x test --no-daemon

# jar 복사 (jar 이름 고정되어 있다고 가정)
COPY build/libs/backend-0.0.1-SNAPSHOT.jar

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

