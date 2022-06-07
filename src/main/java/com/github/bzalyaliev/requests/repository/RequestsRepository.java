package com.github.bzalyaliev.requests.repository;

import com.github.bzalyaliev.requests.repository.entity.RequestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepository extends JpaRepository<RequestsEntity, Long> {
}
