package zamyslov.centreit.testtask.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zamyslov.centreit.testtask.entity.WeatherIndicator;

/***
 * DAO сущности Погодный показатель
 */
@Repository
public interface WeatherIndicatorRepository extends CrudRepository<WeatherIndicator, Long> {
}
