package com.example.demo.services;

import com.example.demo.converters.StudentConverter;
import com.example.demo.domain.Student;
import com.example.demo.dtos.StudentDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.StudentRepository;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
//    private final StudentConverter studentConverter;

    //@Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAllNoAttachment();
    }
    //@Override
    public List<StudentDto> getAllStudentsNoAttachment(){
        return studentRepository.findAllNoAttachment();
    }
}

