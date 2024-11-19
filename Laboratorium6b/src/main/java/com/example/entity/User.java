package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Name is required.")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters.")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Surname is required.")
    @Size(min = 2, max = 255, message = "Surname must be between 2 and 255 characters.")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "Login is required.")
    @Size(min = 4, max = 255, message = "Login must be between 5 and 255 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login can only contain alphanumeric characters.")
    @Column(name = "login", unique = true)  // Enforce uniqueness at the database level
    private String login;

    @NotNull(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    @Column(name = "password")
    private String password;

    public User() {}

    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}