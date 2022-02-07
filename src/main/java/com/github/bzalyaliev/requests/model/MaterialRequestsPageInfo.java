package com.github.bzalyaliev.requests.model;

import com.github.bzalyaliev.requests.repository.RequestsEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Builder
@Data
@Value
public class MaterialRequestsPageInfo {
    Long totalElements;
    int currentPage;
    int totalPages;
    List<RequestsEntity> requests;

}
