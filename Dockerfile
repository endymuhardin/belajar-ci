FROM openjdk:latest
ADD target/belajar-ci.jar /opt/app.jar
RUN bash -c 'touch /opt/app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","/opt/app.jar"]