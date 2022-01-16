package com.github.bzalyaliev.requests.controller;

import com.github.bzalyaliev.requests.model.Requests;
import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class RequestsController {
    private final RequestsRepository requestsRepository;

    @PostMapping(value = "/requests/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newRequest(@Valid @RequestBody Requests requests) {
        requestsRepository.save(RequestsEntity.builder()
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

    @GetMapping(value = "/requests/{id}")
    RequestsEntity oneRequest(@PathVariable Long id) {
        return requestsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find request"));
    }


    @GetMapping(value = "/requests")
    Iterable allRequests(){
        return requestsRepository.findAll();
    }









}
