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
                .date(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.FLAKES)
                .mass(29.7)
                .deadline(ZonedDateTime.of(2022, 2, 1, 0, 0, 0, 0, ZoneId.of("Europe/Moscow")))
                .objective("Test Carbon")
                .comments("My comment for testing")
                .build();
        requestsEntity = repository.save(requestsEntity);

        System.out.println(requestsEntity.getId().toString() + " " + requestsEntity.getDate() + " " + requestsEntity.getMass() + " " + requestsEntity.getDeadline());

        given()
                .when()
                .get("/requests")
                .then()
                .log().all()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].id", equalTo(requestsEntity.getId().intValue()))
                .body("[0].date", lessThanOrEqualTo(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+03:00"))))
                .body("[0].status", equalTo(Status.DONE.name()))
                .body("[0].originator", equalTo("Bulat Zalyaliev"))
                .body("[0].type", equalTo(Type.FLAKES.name()))
                .body("[0].mass", equalTo(29.7F))
                .body("[0].deadline", equalTo("2022-02-01T00:00:00+03:00"))
                .body("[0].objective", equalTo("Test Carbon"))
                .body("[0].comments", equalTo("My comment for testing"))
        ;
    }

    @Test
    void itCreatesRequest() {
        Request request = Request
                .builder()
                .date(ZonedDateTime.now(ZoneId.of("Europe/London")).truncatedTo(ChronoUnit.SECONDS))
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.FLAKES)
                .mass(29.7)
                .deadline(ZonedDateTime.of(2022, 2, 1, 0, 0, 0, 0, ZoneId.of("Europe/London")).truncatedTo(ChronoUnit.SECONDS))
                .objective("Test Carbon")
                .comments("My comment for testing")
                .build();

        System.out.println(request.getDate() + " " + request.getDeadline());

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/request")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(
                        "id", notNullValue(),
                        "date", lessThanOrEqualTo((ZonedDateTime.now(ZoneId.of("Europe/London")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))),
                        "status", equalTo(Status.DONE.name()),
                        "originator", equalTo("Bulat Zalyaliev"),
                        "type", equalTo(Type.FLAKES.name()),
                        "mass", equalTo(29.7F),
                        "deadline", equalTo("2022-02-01T00:00:00Z"),
                        "objective", equalTo("Test Carbon"),
                        "comments", equalTo("My comment for testing")
                );
    }

    @Test
    void itUpdatesRequest() {
        RequestsEntity requestsEntity = RequestsEntity
                .builder()
                .date(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.POWDER)
                .mass(35.5)
                .deadline(ZonedDateTime.of(2022, 2, 01, 0, 0, 0, 0, ZoneId.of("Europe/London")))
                .objective("Test Carbon")
                .comments("My comment for testing")
                .build();
        requestsEntity = repository.save(requestsEntity);

        System.out.println(requestsEntity.getId().toString() + " " + requestsEntity.getDate() + " " + requestsEntity.getMass() + " " + requestsEntity.getDeadline());

        Request updateRequest = Request
                .builder()
                .date(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .status(Status.DONE)
                .originator("Adel Zalyaliev")
                .type(Type.FLAKES)
                .mass(35.5)
                .deadline(ZonedDateTime.of(2022, 2, 14, 0, 0, 0, 0, ZoneId.of("Europe/London")))
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
                        "date", lessThanOrEqualTo((ZonedDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+03:00")))),
                        "status", equalTo(Status.DONE.name()),
                        "originator", equalTo("Adel Zalyaliev"),
                        "type", equalTo(Type.FLAKES.name()),
                        "mass", equalTo(35.5F),
                        "deadline", equalTo("2022-02-14T00:00:00Z"),
                        "objective", equalTo("Test Carbon"),
                        "comments", equalTo("My comment for update testing")
                );
    }

    @Test
    void itDeletesRequest() {
        RequestsEntity requestsEntity = RequestsEntity
                .builder()
                .date(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .status(Status.DONE)
                .originator("Bulat Zalyaliev")
                .type(Type.FLAKES)
                .mass(29.7)
                .deadline(ZonedDateTime.of(2022, 2, 1, 0, 0, 0, 0, ZoneId.of("Europe/Moscow")))
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


