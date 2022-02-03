package com.github.bzalyaliev.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RequestsRepository extends CrudRepository<RequestsEntity, Long>, JpaRepository<RequestsEntity, Long> {


}
