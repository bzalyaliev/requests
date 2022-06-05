package com.github.bzalyaliev.requests.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "auth_user")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserEntity {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long userId;

    @Column(name = "createdAt", nullable = false)
    ZonedDateTime date;

    @Column(name = "firstName", nullable = false)
    String firstName;

    @Column(name = "lastName", nullable = false)
    String lastName;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "login", nullable = false)
    String login;

    @ManyToOne(optional = true)
    @JoinColumn(insertable = false, updatable = false, table = "auth_role", name="roleName")
    Role role;

}
