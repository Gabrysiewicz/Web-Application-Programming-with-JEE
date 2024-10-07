package gabrysiewicz.laboratorium5world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableAutoConfiguration
@ComponentScan({"controllers"})
@EnableJpaRepositories({"repositories"}) 
@EntityScan(basePackages = "entities") // !!!
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}