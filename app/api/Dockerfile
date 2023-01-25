FROM openjdk:17-jdk

RUN mkdir /opt/koi-wiki

WORKDIR /opt/koi-wiki

ADD target/wiki-api-*.jar ./api.jar

# Run l'app
CMD java -jar ./api.jar
