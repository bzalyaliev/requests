# Requests
Service for managing requests for material.

----------------------------------
### Technological stack

#### Backend:
- Java 11. 
- Spring Boot. 
- PostgreSQL.

#### Frontend:
- React.Js.

#### Containerization:
- Docker.

#### Testing:
- Testcontainers-spring-boot.
- Rest-assured.
- JUnit5.

#### Repositories:
- DockerHub.
----------------------------------
### Endpoints

#### hostname/request

Expected form with fields:
- Date (current date).
- Originator.
- Type.
- Necessary mass, g
- Objective of use.
- Deadline.
- Comments.
- Button"Send request".
All fields except Comments are required

#### hostname/requests

Expected table with columns:
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

#### 1. Docker container with database:

- Run container "my_postgres" with PostgreSQL:
```
docker run -d --name my_postgres -v my_dbdata:/var/lib/postgresql/data -p 54320:5432 -e POSTGRES_PASSWORD=my_password postgres:13
```

- For connection to container "my_postgres":

Mac OS:
```
sudo docker exec -it my_postgres psql -U postgres
```                 

Windows/GitBash:
```
winpty docker exec -it my_postgres psql -U postgres
```

- For creation database "requests":
```
CREATE DATABASE requests;
```


#### 2. Application from Idea:

* Run RequestsApplication class as usual
* Build frontend-for-backend running from the root dir: `sh ./infrastructure/rebuild-frontend-for-backend.sh`
* Enjoy at http://localhost:8080

#### 3. Application as standalone jar

* Build it
```shell
./mvnw clean package -Dmaven.test.skip=true

java -jar ./target/requests-0.0.1-SNAPSHOT.jar
```

* Enjoy at http://localhost:8080

#### 4. Application in Docker container

- Build jar:
```
./mvnw clean package
```

- Build image of application with name "requests" and tag "latest":
 ```
./mvnw jib:dockerBuild -Djib.to.image=requests -Djib.to.tags=latest
 ```

- Create network "network_name":
```
docker network create --driver bridge network_name
```

- Run container with database and application in one network "network_name":

```
docker run --name my_postgres --network=network_name -e POSTGRES_PASSWORD=my_password -d postgres:13
````

```
docker run -p 8080:8080 --network=network_name -e SPRING_DATASOURCE_URL=jdbc:postgresql://my_postgres:5432/requests requests:latest
```

- Enjoy at http://localhost:8080/


### How to deploy

#### Server preparation

1. Install Docker engine for your OS.
2. Clone [requests-infrastructure](https://github.com/bzalyaliev/requests-infrastructure) repository on server
3. Create file .env in root of requests-infrastructure and add:
```
POSTGRES_USER=TYPE USERNAME HERE
POSTGRES_PASSWORD=TYPE PASSWORD HERE
```
4. Install Nginx and use configure file from [here](https://github.com/bzalyaliev/requests-infrastructure/blob/main/nginx/requests.config)

#### On your machine:

* build jar and docker image of Spring Boot with tag "latest":

```
./mvnw clean package
```

```
docker login
```

```
./mvnw jib:dockerBuild -Djib.to.image=bulatzalyaliev/requests -Djib.to.tags=latest
```

```
docker push bulatzalyaliev/requests:latest

```

#### On server:

- update containers using new images from Dockerhub
```
sudo docker pull bulatzalyaliev/requests:latest
sudo docker-compose up -d
```
- Checking work with:

```
curl http://localhost:8080/api/requests
```

- Enjoy at https://hostname


