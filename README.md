# todoapp
 Simple Todo application to demonstrate Docker + Couchbase + Spring stack

# Build
To build both database and application containers, you can use two commands:

````bash
mvn clean install -DskipTests

docker compose build
````

If you have [make](https://www.gnu.org/software/make/manual/make.html) installed on your machine, you can simply run:
````bash
make build
````

# Run
After you build both database and application containers, you can use this command to start both database and application:
````bash
docker compose up
````

You can also run database and application containers in different terminals. 
I personally prefer to run database container in seperate terminal and run application without docker to effective development.

To start only database container:
````bash
docker compose up db --build
````

To start only application container:
````bash
docker compose up todo_app --build
````

If you wish to use make, you can use this commands seperately:
````bash
make run      # starts both database and application container
````
````bash
make run-db   # starts only database container
````
````bash
make run-app  # starts only aplication container
````

After running application successfully, Swagger UI will be available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

