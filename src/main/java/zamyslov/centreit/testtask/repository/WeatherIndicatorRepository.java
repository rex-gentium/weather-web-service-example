package zamyslov.centreit.testtask.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zamyslov.centreit.testtask.entity.WeatherIndicator;

@Repository
public interface WeatherIndicatorRepository extends CrudRepository<WeatherIndicator, Long> {
}
