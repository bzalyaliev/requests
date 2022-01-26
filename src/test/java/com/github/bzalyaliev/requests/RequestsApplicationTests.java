package com.github.bzalyaliev.requests;

import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import com.github.bzalyaliev.requests.repository.Status;
import com.github.bzalyaliev.requests.repository.Type;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RequestsApplicationTests {

    @LocalServerPort
    private Integer port;
    @Autowired
    RequestsRepository repository;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @AfterEach
    void deleteAll() {
        repository.deleteAll();
    }

    @Test
    void itReturnsRequests() {
        RequestsEntity requestsEntity = RequestsEntity
                .builder()
                .date(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Moscow")))
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.FLAKES)
                .mass(29.7)
                .deadline(ZonedDateTime.of(2022,2,1,0,0,0,0, ZoneId.of("Europe/Moscow")))
                .objective("Test Carbon")
                .comments("My comment for testing")
                .build();
        requestsEntity = repository.save(requestsEntity);

        given()
                .when()
                .get("/requests")
                .then()
                .log().all()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].id", equalTo(requestsEntity.getId().intValue()))
                .body("[0].date", equalTo(ZonedDateTime.of(requestsEntity.getDate().toLocalDateTime(), ZoneId.of("Europe/Moscow"))))
                .body("[0].status", equalTo(Status.DONE.name()))
                .body("[0].originator", equalTo("Bulat Zalyaliev"))
                .body("[0].type", equalTo(Type.FLAKES.name()))
                .body("[0].mass", equalTo(29.7))
                .body("[0].deadline", equalTo(ZonedDateTime.of(2022,2,1,0,0,0,0, ZoneId.of("Europe/Moscow"))))
                .body("[0].objective", equalTo("Test Carbon"))
                .body("[0].comments", equalTo("My comment for testing"))
                ;
    }

    @Test
    void itCreatesRequest() {
        Request request = Request
                .builder()
                .type(Type.FLAKES)
                .status(Status.DONE)
                .objective("Test Carbon")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/request")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(
                        "id", notNullValue(),
                        "type", equalTo(request.getType().toString()),
                        "status", equalTo(request.getStatus().toString()),
                        "objective", equalTo(request.getObjective())
                );

    }

    @Test
    void itUpdatesRequest() {
        RequestsEntity requestsEntity = RequestsEntity
                .builder()
                .objective("Test Carbon")
                .type(Type.FLAKES)
                .status(Status.DONE)
                .build();
        requestsEntity = repository.save(requestsEntity);

        Request request = Request
                .builder()
                .type(Type.FLAKES)
                .status(Status.DONE)
                .objective("Test Carbon UPDATE")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/request/" + requestsEntity.getId().toString())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "id", notNullValue(),
                        "type", equalTo(request.getType().toString()),
                        "status", equalTo(request.getStatus().toString()),
                        "objective", equalTo(request.getObjective())
                );
    }

    @Test
    void itDeletesRequest() {
        RequestsEntity requestsEntity = RequestsEntity
                .builder()
                .objective("Test Carbon")
                .type(Type.FLAKES)
                .status(Status.DONE)
                .build();
        requestsEntity = repository.save(requestsEntity);

        given()
                .when()
                .delete("/request/" + requestsEntity.getId().toString())
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}


