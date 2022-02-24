package com.cscb634.ejournal.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "teacher")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  Teacher {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private int age;

    private String address;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Subject subject;
}
