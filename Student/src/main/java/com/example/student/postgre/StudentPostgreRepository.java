package com.example.student.postgre;

import com.example.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPostgreRepository extends JpaRepository<Student,Long> {

}
