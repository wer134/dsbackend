# 1. Java 17 베이스 이미지 사용
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 관련 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 4. 소스 코드 복사
COPY src src

# 5. Gradle 빌드 실행 (no-daemon 옵션으로 Render 환경에 최적화)
RUN chmod +x gradlew && ./gradlew build -x test --no-daemon

# 6. 빌드된 JAR 실행
ENTRYPOINT ["java", "-jar", "build/libs/*.jar"]

