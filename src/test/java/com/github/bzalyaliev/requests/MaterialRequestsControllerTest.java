package com.github.bzalyaliev.requests;

import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import com.github.bzalyaliev.requests.repository.Status;
import com.github.bzalyaliev.requests.repository.Type;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MaterialRequestsControllerTest {

    private final ZonedDateTime deadline = ZonedDateTime.of(2022, 2, 1, 0, 0, 0, 0, ZoneId.systemDefault());
    private final ZonedDateTime updateDeadline = ZonedDateTime.of(2022, 2, 14, 0, 0, 0, 0, ZoneId.systemDefault());

    private String currentDateFormatted() {
        return ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    RequestsEntity requestsEntity = RequestsEntity
            .builder()
            .date(ZonedDateTime.now())
            .status(Status.GENERATED)
            .originator("Bulat Zalyaliev")
            .type(Type.FLAKES)
            .mass(29.7)
            .deadline(deadline)
            .objective("Test Carbon")
            .comments("My comment for testing")
            .build();

    private final MaterialRequests nullMaterialRequests = MaterialRequests
            .builder()
            .date(ZonedDateTime.now())
            .status(null)
            .originator(null)
            .type(null)
            .mass(null)
            .deadline(null)
            .objective(null)
            .comments(null)
            .build();

    private final MaterialRequests materialRequests = MaterialRequests
            .builder()
            .date(ZonedDateTime.now(ZoneId.systemDefault()))
            .status(Status.GENERATED)
            .originator("Bulat Zalyaliev")
            .type(Type.FLAKES)
            .mass(29.7)
            .deadline(deadline)
            .objective("Test Carbon")
            .comments("My comment for testing")
            .build();

    private final MaterialRequests updateMaterialRequests = MaterialRequests
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
        requestsEntity = repository.save(requestsEntity);
        given()
                .when()
                .get("/requests")
                .then()
                .log().all()
                .statusCode(200)
                .body("totalElements", equalTo(1))
                .body("currentPage", equalTo(0))
                .body("totalPages", equalTo(1))
                .body("requests.id[0]", equalTo(requestsEntity.getId().intValue()))
                .body("requests.date[0]", lessThanOrEqualTo(currentDateFormatted()))
                .body("requests.status[0]", equalTo(Status.GENERATED.name()))
                .body("requests.originator[0]", equalTo("Bulat Zalyaliev"))
                .body("requests.type[0]", equalTo(Type.FLAKES.name()))
                .body("requests.mass[0]", equalTo(29.7F))
                .body("requests.deadline[0]", equalTo(deadline.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
                .body("requests.objective[0]", equalTo("Test Carbon"))
                .body("requests.comments[0]", equalTo("My comment for testing"))
        ;
    }

    @Test
    void itReturnsNotFoundExceptionForNotFoundRequest() {
        requestsEntity = repository.save(requestsEntity);
        given()
                .when()
                .get("/request/10")
                .then()
                .log().all()
                .statusCode(404)
                .body(
                        "type", equalTo("NotFoundException"),
                        "detailedMessage", equalTo("Could not find request"),
                        "status", equalTo(404));

    }

    @Test
    void itCreatesRequest() {
        given()
                .contentType(ContentType.JSON)
                .body(materialRequests)
                .when()
                .post("/request")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(
                        "id", notNullValue(),
                        "date", lessThanOrEqualTo(currentDateFormatted()),
                        "status", equalTo(Status.GENERATED.name()),
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
        requestsEntity = repository.save(requestsEntity);
        given()
                .contentType(ContentType.JSON)
                .body(updateMaterialRequests)
                .when()
                .patch("/request/" + requestsEntity.getId().toString())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "id", notNullValue(),
                        "date", lessThanOrEqualTo(currentDateFormatted()),
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
    void itReturnsNotFoundExceptionForUpdateNotFoundRequest() {
        requestsEntity = repository.save(requestsEntity);
        given()
                .contentType(ContentType.JSON)
                .body(updateMaterialRequests)
                .when()
                .get("/request/10")
                .then()
                .log().all()
                .statusCode(404)
                .body(
                        "type", equalTo("NotFoundException"),
                        "detailedMessage", equalTo("Could not find request"),
                        "status", equalTo(404));

    }

    @Test
    void itDeletesRequest() {
        requestsEntity = repository.save(requestsEntity);
        given()
                .when()
                .delete("/request/" + requestsEntity.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void itReturnsInternalServerErrorForDeleteNotFoundRequest() {
        requestsEntity = repository.save(requestsEntity);
        given()
                .when()
                .delete("/request/10")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(
                        "type", equalTo("EmptyResultDataAccessException"),
                        "detailedMessage", equalTo("No class com.github.bzalyaliev.requests.repository.RequestsEntity entity with id 10 exists!"),
                        "status", equalTo(500));

    }

    @Test
    void itCreatesNullRequest() {
        given()
                .contentType(ContentType.JSON)
                .body(nullMaterialRequests)
                .when()
                .post("/request")
                .then()
                .statusCode(400)
        ;
    }

    @Test
    void itUpdatesNullRequest() {
        requestsEntity = repository.save(requestsEntity);
        given()
                .contentType(ContentType.JSON)
                .body(nullMaterialRequests)
                .when()
                .patch("/request/" + requestsEntity.getId().toString())
                .then()
                .statusCode(400)
        ;
    }
}

