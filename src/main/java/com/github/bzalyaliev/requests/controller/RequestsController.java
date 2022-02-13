package com.github.bzalyaliev.requests.controller;

import com.github.bzalyaliev.requests.model.MaterialRequests;

import com.github.bzalyaliev.requests.model.MaterialRequestsPageInfo;
import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import com.github.bzalyaliev.requests.repository.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


import javax.validation.Valid;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class RequestsController {
    private final RequestsRepository requestsRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @PostMapping(value = "/request")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestsEntity newRequest(@Valid @RequestBody MaterialRequests materialRequests) {
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
    public RequestsEntity patchBatch(@PathVariable Long id, @Valid @RequestBody MaterialRequests requests) {
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
    public ResponseEntity<MaterialRequestsPageInfo> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "status,asc", name = "sort") List<String> sortQueries
    ) {

        List<Order> orders = SortingUtil.sortQueriesToOrder(sortQueries);

        if (sortQueries[0].contains(",")) {
            for (String sortQuery : sortQueries) {
                String[] splitSortQuery = sortQuery.split(",");
                if (!splitSortQuery[1].isEmpty()) {
                    orders.add(new Order(getSortDirection(splitSortQuery[1]), splitSortQuery[0]));
                } else {
                    orders.add(new Order(Sort.Direction.DESC, splitSortQuery[0]));
                }
            }
        } else {
            orders.add(new Order(getSortDirection(sortQueries[1]), sortQueries[0]));
        }

        Pageable paging = PageRequest.of(page, size, Sort.by(orders));
        Page<RequestsEntity> pageRequests = requestsRepository.findAll(paging);
        List<RequestsEntity> requests = pageRequests.getContent();

        MaterialRequestsPageInfo materialRequestPageInfo = MaterialRequestsPageInfo
                .builder()
                .requests(requests)
                .totalElements(pageRequests.getTotalElements())
                .totalPages(pageRequests.getTotalPages())
                .currentPage(pageRequests.getNumber())
                .build();

        return new ResponseEntity<>(materialRequestPageInfo, HttpStatus.OK);
    }
}

