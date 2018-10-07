package zamyslov.centreit.testtask.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zamyslov.centreit.testtask.entity.City;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
}
