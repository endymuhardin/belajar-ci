FROM openjdk:latest
ADD target/belajar-ci.jar /opt/app.jar
ADD gcloud-credential.json /opt/gcloud-credential.json
ENV GOOGLE_APPLICATION_CREDENTIALS=/opt/gcloud-credential.json
RUN bash -c 'touch /opt/app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app.jar"]