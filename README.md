# Requests
Service for managing requests for material. Based on Java, Spring Boot, PostgreSQL, React, Docker.

### Endpoints

hostname/request

Form with fields:
- Date (current date).
- Originator.
- Type.
- Necessary mass, g
- Objective of use.
- Deadline.
- Comments.
- Button"Send request".
All fields except Comments are required


hostname/requests

Table Waiting List with columns:
- No
- Date
- Status (Generated, In Work, Done, Cancelled, Rejected)
- Originator
- Type
- Necessary mass, g
- Objective of use.
- Deadline.
- Comments.

----------------------------------
### How to run

1. Idea:

* Run RequestsApplication class as usual
* Build frontend-for-backend running from the root dir: `sh ./infrastructure/rebuild-frontend-for-backend.sh`
* Enjoy at http://localhost:8080

2. As standalone jar

* Build it
```shell
./mvnw clean package -Dmaven.test.skip=true

java -jar ./target/requests-0.0.1-SNAPSHOT.jar
```

* Enjoy at http://localhost:8080

3. As local network with Docker 
* create docker-compose file
```shell
version: "3.7"
 services:
   material-requests:
     build: .
     restart: always
     ports:
       - 8080:8080
     depends_on:
       - postgres_db
   postgres_db:
     image: "postgres:13"
     restart: always
     ports:
       - 5432:54320
     environment:
       POSTGRES_DB: requests
       POSTGRES_PASSWORD: [TYPE YOUR DB PASSWORD HERE]
```
* Build jar and create docker image of spring boot application (with jib)

```shell
./mvnw jib:dockerBuild -Djib.to.image=[IMAGE NAME]:[IMAGE TAG]
```

* Create network with name "network_name"
```shell
docker network create --driver bridge network_name
```

* Build and run docker container with postgresql with name my_postgres
```shell
docker run -d --name my_postgres -v my_dbdata:/var/lib/postgresql/data -p 54320:5432 -e POSTGRES_PASSWORD=my_password postgres:13
```

* Run docker container with postgresql in network = "network_name"

```shell
docker run --name my_postgres --network=network_name -e POSTGRES_PASSWORD=my_password -d postgres:13
```

* Run docker image of application [IMAGE NAME]:[IMAGE TAG] on 8090 port in "network_name" network
```shell
docker run -p 8090:8080 --network=network_name -e SPRING_DATASOURCE_URL=jdbc:postgresql://my_postgres:5432/requests [IMAGE NAME]:[IMAGE TAG]
```
* Enjoy at http://localhost:8090

### How to deploy
My pipeline:

* docker-compose file should be on server (from requests-infrastructure repository on my github)

* login to the docker hub
```shell
docker login 
```
* build jar and docker image of Spring Boot app with name [DOCKER ID]/[DOCKER HUB REPOSITORY NAME]:[TAG]

```shell
./mvnw jib:dockerBuild -Djib.to.image=[DOCKER ID]/[DOCKER HUB REPOSITORY NAME]:[TAG]
```
* push image with app to the Docker Hub
```shell
docker push [DOCKER ID]/[DOCKER HUB REPOSITORY NAME]:[TAG]
```
* on the server pull image with app 
```shell
docker pull [DOCKER ID]/[DOCKER HUB REPOSITORY NAME]:[TAG]
```

* go to the folder with docker-compose file and run containers with database and app
```shell
docker-compose up -d
```
* check get requests on server
```shell
curl http://localhost:8080/api/requests
```

* http://hostname:8080/api/requests - enjoy
use your hostname