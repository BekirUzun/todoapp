build:
	mvn clean install -DskipTests
	docker compose build

build-app:
	mvn clean install -DskipTests
	docker compose build todo_app

build-db:
	docker compose build db

build-app2:
	docker build -f Dockerfile.app -t todo-app --target build .

build-plain:
	docker build -t todo-app --target build --progress plain .

run:
	mvn clean install -DskipTests
	docker compose up --build

run:
	docker compose up db --build --detach
	sleep 30
	mvn clean install -DskipTests
	docker compose up todo_app --build

run-win:
	docker compose up db --build --detach
	timeout 30
	mvn clean install -DskipTests
	docker compose up todo_app --build

test:
	@echo hello
	timeout 3
	@echo world

run-app:
	mvn clean install -DskipTests
	docker compose up todo_app --build --force-recreate

run-db:
	docker compose up db --build