FROM adoptopenjdk:11-jre-hotspot

EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]

ADD /subprojects/web/build/libs/app.jar /app.jar


