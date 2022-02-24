package com.cscb634.ejournal.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "subject")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    private UUID id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ElementCollection(targetClass = Integer.class)
    @CollectionTable(name = "subject_grades", joinColumns = @JoinColumn(name = "subject_id"))
    @Column(name = "grade_value")
    private List<Integer> grade;

    private int absence;
}
