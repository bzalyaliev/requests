package com.github.bzalyaliev.requests;

import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class MaterialRequestRepositoryTest {

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
