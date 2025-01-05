package com.example.demo.services;

import com.example.demo.domain.Student;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.StudentRepository;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

//    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}

