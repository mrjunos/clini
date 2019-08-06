package com.mrjunos.clini.JPA;

import com.mrjunos.clini.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
