
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

Assuming that property names and values in README.md needs to be tested against the values which are returned as result:

1. "subject" values from the README.md don't exist on the server
2. When Query include "properties" all objects are returned, regarding the given "properties"
3. Metadata is returning additional properties: "url", "ticker", "decimals", "policy", "logo"
4. Metadata is missing "preImage" property
5. For the property "name" there is a difference in following properties:
   * "signatures" and "anSignatures" value.
   * There is additional "sequenceNumber" property (in all objects)
   
