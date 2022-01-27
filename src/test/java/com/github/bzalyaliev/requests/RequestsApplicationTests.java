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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RequestsApplicationTests {

    private ZonedDateTime deadline = ZonedDateTime.of(2022, 2, 1, 0, 0, 0, 0, ZoneId.systemDefault());
    private ZonedDateTime updateDeadline = ZonedDateTime.of(2022, 2, 14, 0, 0, 0, 0, ZoneId.systemDefault());

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
                .date(ZonedDateTime.now())
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.FLAKES)
                .mass(29.7)
                .deadline(deadline)
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
                .body("[0].date", lessThanOrEqualTo(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
                .body("[0].status", equalTo(Status.DONE.name()))
                .body("[0].originator", equalTo("Bulat Zalyaliev"))
                .body("[0].type", equalTo(Type.FLAKES.name()))
                .body("[0].mass", equalTo(29.7F))
                .body("[0].deadline", equalTo(deadline.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
                .body("[0].objective", equalTo("Test Carbon"))
                .body("[0].comments", equalTo("My comment for testing"))
        ;
    }

    @Test
    void itCreatesRequest() {
        Request request = Request
                .builder()
                .date(ZonedDateTime.now(ZoneId.systemDefault()))
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.FLAKES)
                .mass(29.7)
                .deadline(deadline)
                .objective("Test Carbon")
                .comments("My comment for testing")
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
                        "date", lessThanOrEqualTo((ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))),
                        "status", equalTo(Status.DONE.name()),
                        "originator", equalTo("Bulat Zalyaliev"),
                        "type", equalTo(Type.FLAKES.name()),
                        "mass", equalTo(29.7F),
                        "deadline", equalTo(deadline.format(DateTimeFormatter.ISO_INSTANT)),
                        "objective", equalTo("Test Carbon"),
                        "comments", equalTo("My comment for testing")
                );
    }

    @Test
    void itUpdatesRequest() {
        RequestsEntity requestsEntity = RequestsEntity
                .builder()
                .date(ZonedDateTime.now())
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.POWDER)
                .mass(35.5)
                .deadline(deadline)
                .objective("Test Carbon")
                .comments("My comment for testing")
                .build();
        requestsEntity = repository.save(requestsEntity);

        Request updateRequest = Request
                .builder()
                .date(ZonedDateTime.now())
                .status(Status.DONE)
                .originator("Adel Zalyaliev")
                .type(Type.FLAKES)
                .mass(35.5)
                .deadline(updateDeadline)
                .objective("Test Carbon")
                .comments("My comment for update testing")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .patch("/request/" + requestsEntity.getId().toString())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "id", notNullValue(),
                        "date", lessThanOrEqualTo((ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))),
                        "status", equalTo(Status.DONE.name()),
                        "originator", equalTo("Adel Zalyaliev"),
                        "type", equalTo(Type.FLAKES.name()),
                        "mass", equalTo(35.5F),
                        "deadline", equalTo(updateDeadline.format(DateTimeFormatter.ISO_INSTANT)),
                        "objective", equalTo("Test Carbon"),
                        "comments", equalTo("My comment for update testing")
                );
    }

    @Test
    void itDeletesRequest() {
        RequestsEntity requestsEntity = RequestsEntity
                .builder()
                .date(ZonedDateTime.now())
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.FLAKES)
                .mass(29.7)
                .deadline(deadline)
                .objective("Test Carbon")
                .comments("My comment for testing")
                .build();
        requestsEntity = repository.save(requestsEntity);

        given()
                .when()
                .delete("/request/" + requestsEntity.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}


