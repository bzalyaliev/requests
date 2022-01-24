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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RequestsApplicationTests {

    //private static String requestBody = "{\"status\": \"DONE\", \"objective\":\"Test Carbon\", \"type\":\"FLAKE\"}";

    @LocalServerPort
    private Integer port;
    @Autowired
    RequestsRepository repository;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    /*@Test
    void itReturnsRequests() {
        RequestsEntity requestsEntity = RequestsEntity
                .builder()
                .objective("Test Carbon")
                .type(Type.FLAKES)
                .status(Status.DONE)
                .build();
        requestsEntity = repository.save(requestsEntity);

        given()
                .when()
                .get("/requests")
                .then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].id", equalTo(requestsEntity.getId().intValue()))
                .body("[0].type", equalTo(Type.FLAKES.name()))
                .body("[0].objective", equalTo("Test Carbon"))
                .body("[0].status", equalTo(Status.DONE.name()));
    }*/


    /*@Test
    void itReturnsRequest() {
        given()
                .when()
                .get("/request/1")
                .then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].id", equalTo(1))
                .body("[0].type", equalTo(Type.FLAKES.name()))
                .body("[0].objective", equalTo("Test Carbon"))
                .body("[0].status", equalTo(Status.DONE.name()));
    }*/

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
                        "type", equalTo(request.getType().toString()),
                        "status", equalTo(request.getStatus().toString()),
                        "objective", equalTo(request.getObjective())
                );
    }

    @Test
    void itUpdatesRequest() {
        Request request = Request
                .builder()
                .type(Type.FLAKES)
                .status(Status.DONE)
                .objective("Test Carbon Update")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/request/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "type", equalTo(request.getType().toString()),
                        "status", equalTo(request.getStatus().toString()),
                        "objective", equalTo(request.getObjective())
                );
    }
}


