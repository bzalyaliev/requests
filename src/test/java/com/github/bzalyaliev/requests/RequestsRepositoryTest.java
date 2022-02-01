package com.github.bzalyaliev.requests;

import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RequestsRepositoryTest {

    RequestsEntity requestNull = RequestsEntity
            .builder()
            .date(null)
            .status(null)
            .originator(null)
            .type(null)
            .mass(null)
            .deadline(null)
            .objective(null)
            .comments("My comment for testing")
            .build();


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
    void itReturnsNullRequests() {
        RequestsEntity requestsEntity = repository.save(requestNull);
        given()
                .when()
                .get("/requests")
                .then()
                .log().all()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].id", equalTo(requestsEntity.getId().intValue()))
                .body("[0].date", notNullValue())
                .body("[0].status", notNullValue())
                .body("[0].originator", notNullValue())
                .body("[0].type", notNullValue())
                .body("[0].mass", notNullValue())
                .body("[0].deadline", notNullValue())
                .body("[0].objective", notNullValue())
                .body("[0].comments", equalTo("My comment for testing"))
        ;
    }

}
