# WeatherForecast

Application for Weather purposes

The base project uses lombok
In order to call the Api, it is necessary an ApiKey, I used mine, it is set in application.properties

Use Java 21 and Maven 3.9.9 to build and run the project with the following command:
- mvn clean install
- mvn package && java -jar target/forecast-1.0.0-SNAPSHOT.jar

You can also use docker:
- docker build -t forecast/weather-app .
- docker run -p 9002:9002 forecast/weather-app

Test with postman, there is a collection in src / main / resources / postman

The base URL is http://localhost:9002/weather, after which there are the following APIs:

/forecast/{cityName}: Retrieve weather forecast information

