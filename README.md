# WeatherForecast
Application for Weather purposes

The base project uses lombok
In order to call the Api, it is necessary an ApiKey, I used mine, it is set in application.properties

Use Java 21 and Maven 3.9.9 to build the project with the following command:
mvn clean install

Use maven  to run the application: mvnw spring-boot:run 

Test with postman, there is a collection in src / main / resources / postman

The base URL is http://localhost:9002/weather, after which there are the following APIs:

/forecast/{cityName}: Retrieve weather forecast information

