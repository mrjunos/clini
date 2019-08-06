package com.mrjunos.clini.rest;

import com.mrjunos.clini.exception.NotFoundException;
import com.mrjunos.clini.jpa.StudentRepository;
import com.mrjunos.clini.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public Student getStudentByID(@PathVariable Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        } else {
            throw new NotFoundException("Student not found with id: " + id);
        }
    }

    @PostMapping("/students")
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentUpdate) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(studentUpdate.getName());
                    student.setAge(studentUpdate.getAge());
                    return studentRepository.save(student);
                }).orElseThrow(() -> new NotFoundException("Student not found with id " + id));
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return "Delete Succesfully!";
                }).orElseThrow(() -> new NotFoundException("Student not found with id " + id));
    }
}
