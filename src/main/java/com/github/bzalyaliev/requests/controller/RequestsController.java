package com.github.bzalyaliev.requests.controller;

import com.github.bzalyaliev.requests.model.Requests;
import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RequestsController {
    private final RequestsRepository requestsRepository;

    @PostMapping(value = "/request")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestsEntity newRequest(@Valid @RequestBody Requests requests) {
        return requestsRepository.save(RequestsEntity.builder()
                .date(requests.getDate())
                .status(requests.getStatus())
                .originator(requests.getOriginator())
                .type(requests.getType())
                .mass(requests.getMass())
                .deadline(requests.getDeadline())
                .objective(requests.getObjective())
                .comments(requests.getComments())
                .build()
        );
    }

    @GetMapping(value = "/request/{id}")
    public RequestsEntity oneRequest(@PathVariable Long id) {
        return requestsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find request"));
    }

    @GetMapping(value = "/requests")
    public Iterable<RequestsEntity> allRequests() {
        return requestsRepository.findAll();
    }

    @DeleteMapping(value = "/request/{id}")
    public void deleteRequest(@PathVariable Long id) {
        requestsRepository.deleteById(id);
    }

    @PatchMapping(value = "/request/{id}")
    public RequestsEntity patchBatch(@PathVariable Long id, @RequestBody Requests requests) {
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

}


