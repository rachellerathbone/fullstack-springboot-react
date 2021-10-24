package com.springboot.fullstack.student;

import com.springboot.fullstack.student.exception.BadRequestException;
import com.springboot.fullstack.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        Boolean emailExists = studentRepository.selectEmailExists(student.getEmail());

        if (emailExists) {
            throw new BadRequestException(
                    "Email " + student.getEmail() + " taken"
            );
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(
                    "Student with id " + studentId + " does not exist."
            );
        }
        studentRepository.deleteById(studentId);
    }
}
