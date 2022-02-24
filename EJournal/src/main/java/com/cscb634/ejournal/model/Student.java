package com.cscb634.ejournal.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private int age;

    private String address;

    private int yearAtSchool;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private List<Subject> subjects;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
}
