package com.github.bzalyaliev.requests.model;

import com.github.bzalyaliev.requests.repository.RequestsEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

@Builder
@Data
public class MaterialRequestPageInfo {
    Long totalElements;
    int currentPage;
    int totalPages;
    List<RequestsEntity> requests;

}
