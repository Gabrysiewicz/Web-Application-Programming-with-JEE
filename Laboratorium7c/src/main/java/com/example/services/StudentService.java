package com.example.services;

import com.example.entities.Student;
import com.example.entities.StudentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getStudentList() {
        return (List<Student>) studentRepository.findAll();
    }
    public Student addStudent(Student student) {
        return studentRepository.save(student); 
    }
    // Metoda do usuwania studenta
    public boolean deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
            return true;
        } else {
            return false;  // Nie znaleziono studenta o podanym ID
        }
    }
    // Metoda do edytowania studenta
    public Student updateStudent(Long id, Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student updatedStudent = existingStudent.get();

            updatedStudent.setName(student.getName());
            updatedStudent.setSurname(student.getSurname());
            updatedStudent.setAverage(student.getAverage());

            return studentRepository.save(updatedStudent); 
        } else {
            return null;
        }
    }
}
