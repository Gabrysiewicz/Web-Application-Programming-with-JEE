package entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {

    @Id  // Adnotacja JPA, oznacza pole jako klucz główny
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatyczne generowanie ID
    private Long id;

    @JsonView  // Mapowanie JSON -> obiekt Java
    private String name;

    @JsonView  // Mapowanie JSON -> obiekt Java
    private String surname;

    @JsonView  // Mapowanie JSON -> obiekt Java
    private double average;
}