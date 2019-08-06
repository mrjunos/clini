package com.mrjunos.clini.rest;

import com.mrjunos.clini.exception.NotFoundException;
import com.mrjunos.clini.jpa.AssignmentRepository;
import com.mrjunos.clini.jpa.StudentRepository;
import com.mrjunos.clini.model.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students/{studentId}/assignments")
    public List<Assignment> getContactByStudentId(@PathVariable Long studentId) {
        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }

        return assignmentRepository.findByStudentId(studentId);
    }

    @PostMapping("/students/{studentId}/assignments")
    public Assignment addAsignment(@PathVariable Long studentId, @Valid @RequestBody Assignment assignment) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    assignment.setStudent(student);
                    return assignmentRepository.save(assignment);
                }).orElseThrow(() -> new NotFoundException("Student not found!"));
    }

    @PutMapping("/students/{studentId}/assignments/{assignmentId}")
    public Assignment updateAssignment(@PathVariable Long studentId, @PathVariable Long assignmentId, @Valid @RequestBody Assignment assignmentUpdated) {
        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }

        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    assignment.setName(assignmentUpdated.getName());
                    assignment.setGrade(assignmentUpdated.getGrade());
                    return assignmentRepository.save(assignment);
                }).orElseThrow(() -> new NotFoundException("Assignment not found!"));
    }

    @DeleteMapping("/students/{studentId}/assignments/{assignmentId}")
    public String deleteAssignment(@PathVariable Long studentId, @PathVariable Long assignmnentId) {
        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }

        return assignmentRepository.findById(assignmnentId)
                .map(assignment -> {
                    assignmentRepository.delete(assignment);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Contact not found!"));
    }

}
