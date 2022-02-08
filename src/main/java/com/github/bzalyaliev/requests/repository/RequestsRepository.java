package com.github.bzalyaliev.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestsRepository extends CrudRepository<RequestsEntity, Long>, JpaRepository<RequestsEntity, Long>, PagingAndSortingRepository<RequestsEntity, Long> {
}
