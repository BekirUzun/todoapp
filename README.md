# todoapp
 Simple Todo application to demonstrate Docker + Couchbase + Spring stack


# Run

``docker build -t todo-app .``

``docker build -t todo-app --target build .``

``docker compose up``
docker compose up couchbase
docker compose up todo-app

docker run -d --name couchbase -p 8091-8094:8091-8094 -p 11210:11210 couchbase

docker run -p 8080:8080 todo-app

docker compose run -d --service-ports --name todo-app todo-app



docker build -f Dockerfile.app -t todo-app --target build .
docker build -f Dockerfile.db -t couchbase .
docker build -t todo-app --target build --progress plain .