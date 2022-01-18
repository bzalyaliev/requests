package com.github.bzalyaliev.requests.model;

import com.github.bzalyaliev.requests.repository.Status;
import com.github.bzalyaliev.requests.repository.Type;
import lombok.Builder;
import lombok.Value;


import java.time.ZonedDateTime;
import java.util.Date;

@Value
@Builder
public class Requests {
    ZonedDateTime date;
    Status status;
    String originator;
    Type type;
    Double mass;
    ZonedDateTime deadline;
    String objective;
    String comments;
}
