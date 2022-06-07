package com.github.bzalyaliev.requests.repository;

import com.github.bzalyaliev.requests.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
