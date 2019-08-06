package com.mrjunos.clini.jpa;

import com.mrjunos.clini.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
