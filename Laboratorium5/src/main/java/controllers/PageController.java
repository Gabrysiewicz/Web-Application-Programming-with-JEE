package controllers;
import Entity.Zadanie;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import repositories.ZadanieRepository;
@Controller
public class PageController {
    @Autowired
    public ZadanieRepository rep;
    
    @RequestMapping("/")
    @ResponseBody
    public String mainPage() {
        return "Hello Spring Boot from mainPage() method!";
    }
    @RequestMapping("/hello")
    @ResponseBody
    public String pageTwo() {
        return "Hello Spring Boot from pageTwo() method!";
    }
    @RequestMapping("/listaZadan")
    @ResponseBody
    public ResponseEntity<List<Zadanie>> listaZadan() {
        List<Zadanie> zadania = (List<Zadanie>) rep.findAll();
        return ResponseEntity.ok(zadania);
    }
 
    @RequestMapping("/listaZadanAdd")
    @ResponseBody
    public String listaZadan2() {    
        StringBuilder odp = new StringBuilder();
        Zadanie z;
        double k=1000;
        boolean wyk=false;
        for (int i=1;i<=10;i++){
            z = new Zadanie();
            z.setNazwa("zadanie "+i);
            z.setOpis("Opis czynnosci do wykonania w zadaniu "+i);
            z.setKoszt(k);
            z.setWykonane(wyk);
            wyk=!wyk;
            k+=200.50;
            rep.save(z);
            odp.append(z).append("<br>");
        }
        return odp.toString();
    }
    
    @RequestMapping("/delete/{id}")
    public ResponseEntity<String> deleteZadanie(@PathVariable Long id) {
        if (!rep.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zadanie not found with ID: " + id);
        }
        rep.deleteById(id);
        return ResponseEntity.ok("Zadanie with ID " + id + " deleted successfully.");
    }
    @RequestMapping("/wykonane/{wykonane}")
    @ResponseBody
    public List<Zadanie> getZadaniaByWykonane(@PathVariable boolean wykonane) {
        return rep.findByWykonane(wykonane);
        
    }
    @RequestMapping("/kosztLessThan/{koszt}")
    @ResponseBody
    public List<Zadanie> getZadaniaByKosztLessThan(@PathVariable double koszt) {
        return rep.findByKosztLessThan(koszt);
    }

    @RequestMapping("/kosztBetween/{minKoszt}/{maxKoszt}")
    @ResponseBody
    public List<Zadanie> getZadaniaByKosztBetween(@PathVariable double minKoszt, @PathVariable double maxKoszt) {
        return rep.findByKosztBetween(minKoszt, maxKoszt);
    }
}
