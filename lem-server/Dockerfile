FROM openjdk:8
ENV PROJECT_NAME lem-server
VOLUME /tmp
ADD target/$PROJECT_NAME.jar app.jar
ENV ENVTYPE=""
ENTRYPOINT ["sh","-c","java -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom -jar /app.jar  --spring.profiles.active=$ENVTYPE"]
