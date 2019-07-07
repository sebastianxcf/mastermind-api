# Mastermind REST-API

Introduction

Mastermind is a code-breaking game for two
players. One player becomes the codemaker , the
other the codebreaker . The codemaker chooses a
pattern of four color code pegs (duplicates
allowed) and the codebreaker tries to guess it, in
both order and color.
Each guess is made by placing a row of color
code pegs on the decoding board. Once placed,
the codemaker provides feedback by placing from
zero to four key pegs in the small holes of the row
with the guess. A black key peg (small red in the
image) is placed for each code peg from the guess
which is correct in both color and position. A white
key peg indicates the existence of a correct color
code peg placed in the wrong position.

Example: Given a code [RED, BLUE, GREEN, RED] when the codebreaker gives a code with
[RED, GREEN, RED, YELLOW] the feedback will be: 1 black, 2 whites.
For more information about the game: https://en.wikipedia.org/wiki/Mastermind_(board_game)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Java8

Docker (not mandatory)
https://docs.docker.com/install/linux/docker-ce/ubuntu/

## Configuration
Application-prod.properties for prod configuration.
Application-test.properties for test configuration.

The database is a H2 in memory database,to browse the database go 
http://localhost:{server.port}/h2

 Credentials are in the application.propertie files

### Installing

In order to deploy a new version of dev follow the next steps.

Run with docker:
1) mvn -U clean install docker:build
2) docker images
This command will display all your docker images, you should see one named mastermind
3) docker run --rm -p 8080:8080 mastermind

Run just the jar file:
1) mvn -U clean install spring-boot:run

In order to verify the installation, make a post request using Swagger
http://localhost:8080/swagger-ui.html#/

find the endpoint 
/mastermind/new/{codeSize}


## Running the tests

In order to execute the Junit test please run : mvn test 

This will generate the coverage results at :
target/site/jacoco/index.html
You can see the % of coverage of all the classes.
Note that the GameServiceImpl is the core class and has a overage of 97%

## Deployment

In order to deploy a production version please run with active-profile = prod 
docker run -e SPRING_PROFILES_ACTIVE=prod --rm -p 8080:8080 mastermind  --rm -p 8080:8080 mastermind

for


## Authors

* **Sebastian Castellanos** - *Initial work* - [Sebastianxcf](https://github.com/sebastianxcf)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

