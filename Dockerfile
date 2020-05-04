FROM gradle:jdk8-alpine as jdk

USER root

RUN mkdir -p /app/

ADD build/libs/event-streams-demo-1.0.0.jar /app/event-streams-demo-1.0.0.jar
ADD src/main/resources/static/truststore.jks /app/truststore.jks
ADD src/main/resources/static/es-cert.jks /app/es-cert.jks
ADD src/main/resources/static/es-cert-OSDU.jks /app/es-cert-OSDU.jks

WORKDIR /

ENTRYPOINT ["java", "-jar", "/app/event-streams-demo-1.0.0.jar"]