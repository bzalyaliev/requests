package com.github.bzalyaliev.requests;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * This test expects that you have frontend files inside the target/classes/static directory, make sure you have one before running the test
 * OR build it: `mvn clean package`
 * OR just run sh script: `./infrastructure/rebuild-frontend-for-backend.sh`
 *
 * Этот тест ожидает, что ваше фронтенд файлы расположены в target/classes/static директории, убедитесь, что она существует перед запуском
 * ИЛИ соберите это: `mvn clean package`
 * ИЛИ просто запустив sh script: `./infrastructure/rebuild-frontend-for-backend.sh`
 *
 * смотри {@link com.github.bzalyaliev.requests.controller.RedirectToIndexFilter} результаты этого тесты зависят от этого класса
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class ReactControllerTest {

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.port = port;
	}

	/**
	 * Этот тест проверяет, что на root и на "/" доступны HTML ресурсы, теже самые, что используются в промышленной сборке
	 */
	@Test
	void itReturnsIndexHtmlAtRootPath() {
		given()
			.when()
			.get("/")
			.then()
			.statusCode(200)
			.contentType(ContentType.HTML);

		given()
			.when()
			.get()
			.then()
			.statusCode(200)
			.contentType(ContentType.HTML);
	}

	/**
	 * Файл и его расположение точно копирует расположение static файлов в runtime classpath приложения
	 * Этот тест индексирует проблемы или изменения связанные со сборкой статических ресурсов
	 */
	@Test
	void itReturnsStaticJSFileFromTestsClasspath() {
		given()
			.when()
			.get("/static/js/dummy.js")
			.then()
			.statusCode(200)
			.contentType("application/javascript");
	}

}
