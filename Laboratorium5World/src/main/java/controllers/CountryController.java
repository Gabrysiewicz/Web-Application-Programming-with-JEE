package controllers;

import entities.Country;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import repositories.CountryRepository;

@Controller
public class CountryController {
    @Autowired
    public CountryRepository rep;
    
    @RequestMapping("/")
    @ResponseBody
    public String mainPage() {
        return "Hello Spring Boot from mainPage() method!";
    }
    @RequestMapping("/countries")
    @ResponseBody
    public ResponseEntity<List<Country>> countryList() {
        List<Country> countries = (List<Country>) rep.findAll();
        return ResponseEntity.ok(countries);
    }
    @RequestMapping("/countriesFrom/{continent}")
    @ResponseBody
    public List<Country> getCountriesFrom(@PathVariable String continent) {
        return rep.findByContinent(continent);
        
    }
    @RequestMapping("/populationBetween/{min}/{max}")
    @ResponseBody
    public List<Country> getCountriesWithPopulationBetween(@PathVariable Double min,@PathVariable Double max) {
        return rep.findByPopulationBetween(min, max);
    }
    @RequestMapping("/surfaceAreaBetween/{minArea}/{maxArea}")
    @ResponseBody
    public List<Country> getCountriesWithSurfaceAreaBetween(@PathVariable Double minArea,@PathVariable Double maxArea) {
        return rep.findBySurfaceAreaBetween(minArea, maxArea);
    }
 
    
}