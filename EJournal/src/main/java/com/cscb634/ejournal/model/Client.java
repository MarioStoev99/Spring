package com.cscb634.ejournal.model;

import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private UUID id;

    private String firstName;

    private String lastName;

    private int age;

    private String address;

    @Enumerated(EnumType.STRING)
    private Roles role;
}
