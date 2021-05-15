build:
	mvn clean install -DskipTests
	docker compose build

build-app:
	mvn clean install -DskipTests
	docker compose build todo_app

build-db:
	docker compose build db

run:
	mvn clean install -DskipTests
	docker compose up --build

run-app:
	mvn clean install -DskipTests
	docker compose up todo_app --build

run-db:
	docker compose up db --build

run:
	docker compose up

run-safe:
	docker compose up db --build --detach
	sleep 15
	mvn clean install -DskipTests
	docker compose up todo_app --build

run-safe-win:
	docker compose up db --build --detach
	timeout 15
	mvn clean install -DskipTests
	docker compose up todo_app --build