package com.github.bzalyaliev.requests.controller;

import com.github.bzalyaliev.requests.exception.NotFoundException;
import com.github.bzalyaliev.requests.model.MaterialRequest;
import com.github.bzalyaliev.requests.model.MaterialRequestsPageInfoResponse;
import com.github.bzalyaliev.requests.repository.entity.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import com.github.bzalyaliev.requests.repository.entity.Status;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Slf4j

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class RequestsController {
    private final RequestsRepository requestsRepository;

    @PostMapping(value = "/request")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestsEntity newRequest(@Valid @RequestBody MaterialRequest materialRequests) {
        return requestsRepository.save(RequestsEntity.builder()
                .date(ZonedDateTime.now(ZoneId.systemDefault()))
                .status(Status.GENERATED)
                .originator(materialRequests.getOriginator())
                .type(materialRequests.getType())
                .mass(materialRequests.getMass())
                .deadline(materialRequests.getDeadline())
                .objective(materialRequests.getObjective())
                .comments(materialRequests.getComments())
                .build()
        );
    }

    @GetMapping(value = "/request/{id}")
    public RequestsEntity oneRequest(@PathVariable Long id) {
        return requestsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find request"));
    }

    @DeleteMapping(value = "/request/{id}")
    public void deleteRequest(@PathVariable Long id) {
        requestsRepository.deleteById(id);
    }

    @PatchMapping(value = "/request/{id}")
    public RequestsEntity patchBatch(@PathVariable Long id, @Valid @RequestBody MaterialRequest requests) {
        requestsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find request"));

        RequestsEntity requestsEntity = requestsRepository.findById(id).get()
                .setStatus(requests.getStatus())
                .setOriginator(requests.getOriginator())
                .setType(requests.getType())
                .setMass(requests.getMass())
                .setObjective(requests.getObjective())
                .setDeadline(requests.getDeadline())
                .setComments(requests.getComments());
        return requestsRepository.save(requestsEntity);
    }

    @GetMapping("/requests")
    public ResponseEntity<MaterialRequestsPageInfoResponse> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(value = "sort", required = false) String[] sortQueries
    ) {
        Pageable paging;
        if (sortQueries == null) {
            paging = PageRequest.of(page, size);
        } else {
            List<Order> orders = SortingUtil.sortQueriesToOrder(sortQueries);
            paging = PageRequest.of(page, size, Sort.by(orders));
        }

        Page<RequestsEntity> pageRequests = requestsRepository.findAll(paging);
        List<RequestsEntity> requests = pageRequests.getContent();

        MaterialRequestsPageInfoResponse materialRequestPageInfo = MaterialRequestsPageInfoResponse
                .builder()
                .requests(requests)
                .totalElements(pageRequests.getTotalElements())
                .totalPages(pageRequests.getTotalPages())
                .currentPage(pageRequests.getNumber())
                .build();

        return new ResponseEntity<>(materialRequestPageInfo, HttpStatus.OK);
    }
}

