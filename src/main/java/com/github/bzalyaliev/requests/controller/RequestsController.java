package com.github.bzalyaliev.requests.controller;

import com.github.bzalyaliev.requests.model.MaterialRequest;
import com.github.bzalyaliev.requests.model.MaterialRequestPageInfo;
import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import com.github.bzalyaliev.requests.repository.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class RequestsController {
    private final RequestsRepository requestsRepository;

    @PostMapping(value = "/request")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestsEntity newRequest(@Valid @RequestBody MaterialRequest materialRequest) {
        return requestsRepository.save(RequestsEntity.builder()
                .date(ZonedDateTime.now(ZoneId.systemDefault()))
                .status(Status.GENERATED)
                .originator(materialRequest.getOriginator())
                .type(materialRequest.getType())
                .mass(materialRequest.getMass())
                .deadline(materialRequest.getDeadline())
                .objective(materialRequest.getObjective())
                .comments(materialRequest.getComments())
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
    public RequestsEntity patchBatch(@PathVariable Long id, @Valid @RequestBody MaterialRequest materialRequest) {
        requestsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find request"));

        RequestsEntity requestsEntity = requestsRepository.findById(id).get()
                .setOriginator(materialRequest.getOriginator())
                .setType(materialRequest.getType())
                .setMass(materialRequest.getMass())
                .setObjective(materialRequest.getObjective())
                .setDeadline(materialRequest.getDeadline())
                .setComments(materialRequest.getComments());
        return requestsRepository.save(requestsEntity);
    }

    @GetMapping("/requests")
    public ResponseEntity<MaterialRequestPageInfo> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<RequestsEntity> pageRequests = requestsRepository.findAll(paging);
        List<RequestsEntity> requests = pageRequests.getContent();

        MaterialRequestPageInfo materialRequestPageInfo = MaterialRequestPageInfo
                .builder()
                .requests(requests)
                .totalElements(pageRequests.getTotalElements())
                .totalPages(pageRequests.getTotalPages())
                .currentPage(pageRequests.getNumber())
                .build();

        return new ResponseEntity<>(materialRequestPageInfo, HttpStatus.OK);

    }

}





