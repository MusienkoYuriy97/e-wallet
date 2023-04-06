FROM maven:latest AS build
COPY . /be-wallet
WORKDIR /be-wallet
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17
COPY --from=build /be-wallet/target/be-wallet-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","application.jar"]