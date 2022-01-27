package com.github.bzalyaliev.requests.model;

import com.github.bzalyaliev.requests.repository.Status;
import com.github.bzalyaliev.requests.repository.Type;
import lombok.Builder;
import lombok.Value;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;


@Value
@Builder
public class Requests {

    @NotNull
    ZonedDateTime date;

    @NotNull
    Status status;

    @NotNull
    String originator;

    @NotNull
    Type type;

    @Positive
    Double mass;

    @NotNull
    ZonedDateTime deadline;

    @NotNull
    String objective;


    String comments;
}
