package com.example.services;

import com.example.entities.Student;
import com.example.entities.StudentRepository;
import java.util.List;
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
        return studentRepository.save(student);  // Zapisuje obiekt do bazy danych
    }
}
