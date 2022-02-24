package com.cscb634.ejournal.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name = "parent")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parent {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private int age;

    private String address;

    @OneToMany(mappedBy = "parent")
    private List<Student> students;
}
