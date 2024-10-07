// CountryBean.java
package com.example;
import java.io.Serializable;

public class CountryBean implements Serializable {
    // Private fields corresponding to the Country table columns
    private String code;        // Country code
    private String name;        // Country name
    private long population;     // Country population

    // Default constructor
    public CountryBean() {
    }

    // Getter and Setter for code
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for population
    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}
