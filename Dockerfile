# 1단계: Gradle 빌드
FROM gradle:8.2.1-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /home/gradle/project

# 전체 프로젝트 복사
COPY --chown=gradle:gradle . .

# 빌드 실행 (테스트 제외)
RUN ./gradlew build -x test --no-daemon

# 2단계: 실행 환경
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 결과 jar 복사 (이 jar 이름은 실제 경로에 있는 JAR 이름으로 바꿔야 함!)
COPY --from=build /home/gradle/project/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

