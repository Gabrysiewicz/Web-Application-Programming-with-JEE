package controllers;
import Entity.Zadanie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String listaZadan() {
        StringBuilder odp = new StringBuilder();
        Zadanie zadanie = new Zadanie();
        //korzystając z obiektu repozytorium zapisujemy zadanie do bazy
        rep.save(zadanie);
        //korzystając z repozytorium pobieramy wszystkie zadania z bazy
        for(Zadanie i: rep.findAll()) {
            odp.append(i).append("<br>");
        }
        return odp.toString();
    }

}
