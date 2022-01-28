package com.github.bzalyaliev.requests.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

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

    @Column(name = "Date")
    ZonedDateTime date;

    @Column(name = "Status")
    Status status;

    @Column(name = "Originator")
    String originator;

    @Column(name = "Type")
    Type type;

    @Column(name = "Mass")
    Double mass;

    @Column(name = "Deadline")
    ZonedDateTime deadline;

    @Column(name = "Objective")
    String objective;

    @Column(name = "Comments")
    String comments;


}
