package com.github.bzalyaliev.requests.model;

import com.github.bzalyaliev.requests.repository.entity.Status;
import com.github.bzalyaliev.requests.repository.entity.Type;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;


@Value
@Builder
public class MaterialRequest {

    Status status;

    @NotNull
    String originator;

    @NotNull
    Type type;

    @NotNull
    @Positive
    Double mass;

    @NotNull
    ZonedDateTime deadline;

    @NotNull
    String objective;

    String comments;

}

