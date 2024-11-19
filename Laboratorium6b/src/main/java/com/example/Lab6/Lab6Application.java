package com.example.Lab6;

import com.example.dao.UserDao;
import com.example.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;


@EntityScan(basePackages = "com.example.entity")  // Ensures the entity package is scanned
@EnableJpaRepositories(basePackages = "com.example.dao")  // Ensure the repository package is scanned
@SpringBootApplication(scanBasePackages = "com.example")
public class Lab6Application {
    @Autowired
    private UserDao dao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(Lab6Application.class, args);
    }
    @PostConstruct
    public void init() {
        dao.save(new User("Piotr", "Piotrowski","admin", passwordEncoder.encode("admin")));
        dao.save(new User("Ania", "Annowska","ania", passwordEncoder.encode("ania")));
    }
    
}