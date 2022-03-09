# Requests
Service for managing requests for material. Based on Java, Spring Boot, PostgreSQL, React, Docker.

###Endpoints

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

3. As local with Docker (needs to create docker-compose file)

* Build jar

```shell
./mvnw clean package
```

* Create network with name "network_name"
```shell
docker network create --driver bridge network_name
```

* Build docker image of application with name "material-requests"
```shell
docker build . --tag=material-requests:1
```

* Build and run docker container with postgresql with name my_postgres
```shell
docker run -d --name my_postgres -v my_dbdata:/var/lib/postgresql/data -p 54320:5432 -e POSTGRES_PASSWORD=my_password postgres:13
```

* Run docker container with postgresql in network = "network_name"

```shell
docker run --name my_postgres --network=network_name -e POSTGRES_PASSWORD=my_password -d postgres:13
```

* Run docker image of application material-requests:1 on 8090 port in "network_name" network
```shell
docker run -p 8090:8080 --network=network_name -e SPRING_DATASOURCE_URL=jdbc:postgresql://my_postgres:5432/requests material-requests:1
```
* Enjoy at http://localhost:8090

