package com.github.bzalyaliev.requests.controller;

import com.github.bzalyaliev.requests.model.Requests;
import com.github.bzalyaliev.requests.repository.RequestsEntity;
import com.github.bzalyaliev.requests.repository.RequestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    public RequestsEntity newRequest(@Valid @RequestBody Requests requests) {
        return requestsRepository.save(RequestsEntity.builder()
                .date(ZonedDateTime.now(ZoneId.systemDefault()))
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

    /*@GetMapping(value = "/requests")
    public Iterable<RequestsEntity> allRequests() {
        return requestsRepository.findAll();
    }*/

    @DeleteMapping(value = "/request/{id}")
    public void deleteRequest(@PathVariable Long id) {
        requestsRepository.deleteById(id);
    }

    @PatchMapping(value = "/request/{id}")
    public RequestsEntity patchBatch(@PathVariable Long id, @Valid @RequestBody Requests requests) {
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
    public ResponseEntity<Map<String, Object>> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            List<RequestsEntity> requests = new ArrayList<RequestsEntity>();
            Pageable paging = PageRequest.of(page, size);

            Page<RequestsEntity> pageRequests;
            pageRequests = requestsRepository.findAll(paging);
          

            requests = pageRequests.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("requests", requests);
            response.put("currentPage", pageRequests.getNumber());
            response.put("totalItems", pageRequests.getTotalElements());
            response.put("totalPages", pageRequests.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping("/requests/published")
    public ResponseEntity<Map<String, Object>> findByPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<Tutorial> requests = new ArrayList<Tutorial>();
            Pageable paging = PageRequest.of(page, size);

            Page<Tutorial> pageRequests = tutorialRepository.findByPublished(true, paging);
            requests = pageRequests.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("requests", requests);
            response.put("currentPage", pageRequests.getNumber());
            response.put("totalItems", pageRequests.getTotalElements());
            response.put("totalPages", pageRequests.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  ...
}*/
}



