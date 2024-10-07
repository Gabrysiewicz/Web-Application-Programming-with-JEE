package repositories;
import Entity.Zadanie;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
public interface ZadanieRepository extends CrudRepository<Zadanie, Long>{
    // Zwraca rekordy z wykonanymi lub niewykonanymi zadaniami
    List<Zadanie> findByWykonane(boolean wykonane);
    
    // Zwraca rekordy zadań, których koszt jest mniejszy niż zadany
    List<Zadanie> findByKosztLessThan(double koszt);
    
    // Zwraca rekordy zadań, których koszt należy do wskazanego przedziału wartości
    List<Zadanie> findByKosztBetween(double minKoszt, double maxKoszt);
}

