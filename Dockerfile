FROM eclipse-temurin:17-jdk-alpine
MAINTAINER mvm
COPY --from=build /build/libs/service-cdr-api-0.0.1-SNAPSHOT.jar service-cdr-api-0.0.1.jar
RUN sh -c 'touch service-cdr-api-0.0.1.jar'
ENTRYPOINT ["java","-jar","/service-cdr-api-0.0.1.jar"]