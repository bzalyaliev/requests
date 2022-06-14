package com.github.bzalyaliev.requests;

import com.github.bzalyaliev.requests.repository.RoleRepository;
import com.github.bzalyaliev.requests.repository.entity.Role;
import com.github.bzalyaliev.requests.repository.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void itSavesRole() {
        RoleEntity roleEntity = RoleEntity.builder()
                .name(Role.USER)
                .build();

        RoleEntity savedEntity = roleRepository.save(roleEntity);

        Optional<RoleEntity> entityFromRepository = roleRepository.findById(savedEntity.getId());

        assertThat(entityFromRepository).isPresent();
        assertThat(entityFromRepository.get().getId()).isEqualTo(savedEntity.getId());
        assertThat(entityFromRepository.get().getName()).isEqualTo(savedEntity.getName());
    }

}
