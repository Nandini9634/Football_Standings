FROM openjdk:17
EXPOSE 8080
ADD target/footballstanding.jar footballstanding.jar.original 
ENTRYPOINT ["java","-jar","/footballstanding.jar.original"]