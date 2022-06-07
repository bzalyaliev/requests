package com.github.bzalyaliev.requests;

import static org.assertj.core.api.Assertions.*;

import com.github.bzalyaliev.requests.repository.entity.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class MaterialRequestsRepositoryTest {

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

    @Autowired
    RequestsRepository repository;

    @Test
    void itReturnsNullRequests() {
        assertThatThrownBy(() -> repository.saveAndFlush(requestNull))
                .isInstanceOf(DataIntegrityViolationException.class);
    }


}