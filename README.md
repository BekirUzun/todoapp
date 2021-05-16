# todoapp
 Simple Todo application to demonstrate Docker + Couchbase + Spring stack

# Build
To build both database and application containers, you can use two commands:

````bash
mvn clean install -DskipTests

docker compose build
````

# Run
After you build both database and application containers, you can use this command to start both database and application:
````bash
docker compose up
````

You can also run database and application containers in different terminals. 
I personally prefer to run database container in seperate terminal and run application without docker for effective development.

To start only database container:
````bash
docker compose up db --build
````

To start only application container:
````bash
docker compose up todo_app --build
````

# Test
To run application unit tests in **container**, you can use:
````bash
docker build -t todo_app --progress plain --target test .
````

If you want to run tests on your **local machine**, you can use:
````bash
mvn test
````

# make
This project also have a simple Makefile. If you have [make](https://www.gnu.org/software/make/manual/make.html) installed on your machine, you can use this commands:

````bash
make build      # builds both database and application containers

make build-db   # builds only database container

make build-app  # builds only app container

make run        # starts both database and application containers

make run-db     # starts only database container

make run-app    # starts only aplication container

make test       # runs application tests in container
````

# Usage
After running application successfully, Swagger UI will be available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
Basic usage is:
1. Register a user at "/user/register"
2. Login with same credentials at "/user/login"
3. Copy the jwtToken and paste it to Swagger's "Authorize" section
4. You may use Todo endpoints now.


# Troubleshooting
 
1. This app uses custom Swagger UI. If you are experiencing problems with Swagger, you may set springdoc.swagger-ui.custom property to false in application.yml
2. If you are getting timeout exception from Couchbase, default bucket maybe in warmup state. Just give it few minutes to be at healty state.
3. If anyhow creating index on a bucket fail, you may create it manually by running ``CREATE PRIMARY INDEX defaultpk ON default USING GSI`` script on your [Couchbase UI](http://localhost:8091/ui/index.html) (username and password is default **Administrator:password**)