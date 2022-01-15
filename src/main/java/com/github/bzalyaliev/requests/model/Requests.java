package com.github.bzalyaliev.requests.model;

import com.github.bzalyaliev.requests.repository.Originator;
import com.github.bzalyaliev.requests.repository.Status;
import com.github.bzalyaliev.requests.repository.Type;
import lombok.Builder;
import lombok.Value;


import java.util.Date;

@Value
@Builder
public class Requests {
    Date date;
    Status status;
    Originator originator;
    Type type;
    Double mass;
    Date deadline;
    String objective;
    String comments;
}
