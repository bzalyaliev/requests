package com.github.bzalyaliev.requests.model;

import com.github.bzalyaliev.requests.repository.entity.RequestsEntity;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class MaterialRequestsPageInfoResponse {
    Long totalElements;
    int currentPage;
    int totalPages;
    List<RequestsEntity> requests;

}
