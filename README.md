
## Frameworks

* Rest Assured
* Maven
* TestNG

## Dependencies 
The dependencies are stored in pom.xml 

## Project structure 

* Objects for handling Coin and Signature data are stored in src/main/java/models
* Tests are stored in src/test/java/TestServerMock.java

## Test run

Install the pre requirements:
* Java 
* Maven 

To run test in terminal, use "mvn test" command.

### Bugs ###

Endpoint "/metadata/query" returns 500 (Internal Server Error) when it is used POST request.