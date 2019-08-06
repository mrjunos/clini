package com.mrjunos.clini.JPA;

import com.mrjunos.clini.Model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByStudentId(Long studentId);
}
