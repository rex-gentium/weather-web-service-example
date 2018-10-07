package zamyslov.centreit.testtask.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zamyslov.centreit.testtask.entity.City;
import zamyslov.centreit.testtask.entity.WeatherEntry;
import zamyslov.centreit.testtask.entity.WeatherIndicator;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherEntryRepository extends CrudRepository<WeatherEntry, Long> {
    Iterable<WeatherEntry> findAllByCityAndDate(City city, LocalDate date);
    Optional<WeatherEntry> findByCityAndDateAndIndicator(City city, LocalDate date, WeatherIndicator indicator);
}
