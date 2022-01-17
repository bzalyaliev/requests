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
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RequestsController {
    private final RequestsRepository requestsRepository;

    /*@PostMapping(value = "/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public String newRequest(@Valid @RequestBody Requests requests) {
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
        return "requests";
    }*/

    @PostMapping(value = "/requests")
    public String add(@RequestBody Requests requests, Map<String, Object> model) {
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

        Iterable<RequestsEntity> request = requestsRepository.findAll();
        model.put("requests", request);
        return "requests";
    }

    @GetMapping(value = "/requests/{id}")
    RequestsEntity oneRequest(@PathVariable Long id) {
        return requestsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find request"));
    }


    @GetMapping(value = "/requests")
    public String allRequests(Map<String, Object> model) {
        Iterable<RequestsEntity> requests = requestsRepository.findAll();
        model.put("requests", requests);
        return "requests";
    }

}
