# App

This project contains an java maven application with [AWS Java SDK 2.x](https://github.com/aws/aws-sdk-java-v2) dependencies.

## Prerequisites
- Java 1.8+
- Apache Maven


## Development

The program has normal main method. The configured AWS Java SDK client is created in `DependencyFactory` class and you can 
add the code to interact with the SDK client based on your use case.

#### Building the project
```
mvn clean package
```

#### Testing it locally
java -jar target/engines3test.jar a-bucket-name

## Author

Pekka Järveläinen

