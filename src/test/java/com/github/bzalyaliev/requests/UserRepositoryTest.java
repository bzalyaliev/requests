package com.github.bzalyaliev.requests;

import com.github.bzalyaliev.requests.repository.RoleRepository;
import com.github.bzalyaliev.requests.repository.UserRepository;
import com.github.bzalyaliev.requests.repository.entity.Role;
import com.github.bzalyaliev.requests.repository.entity.RoleEntity;
import com.github.bzalyaliev.requests.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class UserRepositoryTest {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void itSavesUser() {
        RoleEntity roleEntity = RoleEntity.builder()
                .name(Role.USER)
                .build();

        RoleEntity savedRoleEntity = roleRepository.save(roleEntity);

        UserEntity userEntity = UserEntity.builder()
                .date(ZonedDateTime.now())
                .firstName("Bulat")
                .lastName("Zalyaliev")
                .email("bulat@he.co")
                .login("bulat")
                .role(roleEntity)
                .build();

        UserEntity savedUserEntity = userRepository.save(userEntity);
        Optional<UserEntity> userEntityFromRepository = userRepository.findById(savedUserEntity.getId());

        assertThat(userEntityFromRepository).isPresent();
        assertThat(userEntityFromRepository.get().getId()).isEqualTo(savedUserEntity.getId());
        assertThat(userEntityFromRepository.get().getLogin()).isEqualTo(savedUserEntity.getLogin());
        assertThat(userEntityFromRepository.get().getRole()).isEqualTo(savedUserEntity.getRole());
    }

    @Test
    public void itShouldNotSaveUserNullableRole() {

        UserEntity userEntity = UserEntity.builder()
                .date(ZonedDateTime.now())
                .firstName("Bulat")
                .lastName("Zalyaliev")
                .email("bulat@he.co")
                .login("bulat")
                .build();

        assertThatThrownBy(() -> userRepository.saveAndFlush(userEntity))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void itShouldNotSavesUserWithRoleThatDoesNotExistsInDatabase() {
        RoleEntity roleEntityUser = RoleEntity.builder()
                .name(Role.USER)
                .build();

        RoleEntity savedRoleEntityUser = roleRepository.save(roleEntityUser);

        RoleEntity roleEntityAdmin = RoleEntity.builder()
                .name(Role.ADMIN)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .date(ZonedDateTime.now())
                .firstName("Bulat")
                .lastName("Zalyaliev")
                .email("bulat@he.co")
                .login("bulat")
                .role(roleEntityAdmin)
                .build();

        assertThatThrownBy(() -> userRepository.saveAndFlush(userEntity))
                .isInstanceOf(DataIntegrityViolationException.class);
    }



}
