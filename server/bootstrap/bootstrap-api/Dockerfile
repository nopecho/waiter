ARG JAVA_VERSION=17
FROM amazoncorretto:${JAVA_VERSION}-alpine

ARG PORT=10001
ARG APP_NAME
ARG FROM_JAR=build/libs/${APP_NAME}.jar

COPY ${FROM_JAR} app.jar

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]