FROM gradle:jdk21-corretto AS build
LABEL authors="daniel.zerain"
WORKDIR /home/gradle/src
COPY . .
RUN gradle clean bootJar --no-daemon

FROM eclipse-temurin:eclipse-temurin:21.0.9_10-jre-alpine-3.22
WORKDIR /app
ENV TZ=America/La_Paz
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]