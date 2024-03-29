package com.github.bzalyaliev.requests.repository;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "requests")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RequestsEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "Date", nullable = false)
    ZonedDateTime date;

    @Column(name = "Status", nullable = false)
    Status status;

    @Column(name = "Originator", nullable = false)
    String originator;

    @Column(name = "Type", nullable = false)
    Type type;

    @Column(name = "Mass", nullable = false)
    Double mass;

    @Column(name = "Deadline", nullable = false)
    ZonedDateTime deadline;

    @Column(name = "Objective", nullable = false)
    String objective;

    @Column(name = "Comments")
    String comments;


}
