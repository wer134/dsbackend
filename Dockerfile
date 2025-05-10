#Use official OpenjDk image
From openjdk:17-jdk-slim

#Set working directory
WORKDIR /app
# Copy build files
COPY build/libs/*.jar app.jar

#Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
