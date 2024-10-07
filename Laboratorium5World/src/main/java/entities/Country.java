package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "country")
public class Country {

    @Id
    private String code; // Kod kraju, klucz główny
    private String name; // Nazwa kraju
    private String continent; // Kontynent
    private Integer population; // Populacja
    @Column(name = "SurfaceArea") // Specify the exact column name
    private Double surfaceArea;

    // Domyślny konstruktor
    public Country() {}

    // Gettery i Settery
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    @Override
     public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                ", population=" + population +
                ", surfaceArea=" + surfaceArea +
                '}'; // Corrected the field name to surfaceArea
    }
}
