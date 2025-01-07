package com.example.demo.controllers;

import com.example.demo.domain.Student;
import com.example.demo.dtos.StudentDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.services.StudentServiceImpl;

@RequiredArgsConstructor
@RequestMapping(value="/students")
@RestController
public class StudentController {
    private final StudentServiceImpl studentService;
    
    @GetMapping
    public List<StudentDto> getAllStudents(){
        return studentService.getAllStudents();
    }
}
