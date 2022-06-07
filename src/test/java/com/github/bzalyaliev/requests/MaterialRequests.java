package com.github.bzalyaliev.requests;

import com.github.bzalyaliev.requests.repository.entity.Status;
import com.github.bzalyaliev.requests.repository.entity.Type;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Builder
@Value
public class MaterialRequests {
    ZonedDateTime date;
    Status status;
    String originator;
    Type type;
    Double mass;
    ZonedDateTime deadline;
    String objective;
    String comments;

}
