FROM gradle:jdk8-alpine as jdk

USER root

RUN mkdir -p /app/

ADD build/libs/event-streams-demo-1.0.0.jar /app/event-streams-demo-1.0.0.jar
ADD src/main/resources/static/es-cert-MSK.jks /app/es-cert-MSK.jks

WORKDIR /

ENTRYPOINT ["java", "-jar", "/app/event-streams-demo-1.0.0.jar"]