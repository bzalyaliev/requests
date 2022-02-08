package com.github.bzalyaliev.requests.model;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.github.bzalyaliev.requests.repository.Type;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class Requests {

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

