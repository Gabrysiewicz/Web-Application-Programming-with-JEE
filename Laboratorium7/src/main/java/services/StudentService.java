package services;

import entities.Student;
import entities.StudentRepository;
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
}

