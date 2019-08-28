FROM openjdk:8
MAINTAINER gfg-productcatalog-services
VOLUME /tmp
ARG JAR_FILE=target/gfg-productcatalog-services.jar
ADD ${JAR_FILE} gfg-productcatalog-services.jar
ENTRYPOINT ["java","-jar","/gfg-productcatalog-services.jar"]