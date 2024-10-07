package repositories;
import entities.Country;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
public interface CountryRepository extends CrudRepository<Country, Long>{

    List<Country> findByContinent(String continent);

    List<Country> findByPopulationBetween(Double min, Double max);

    List<Country> findBySurfaceAreaBetween(Double minArea, Double maxArea);
}