package zamyslov.centreit.testtask.service;

import org.springframework.stereotype.Service;
import zamyslov.centreit.testtask.exception.InternalNullPointerException;
import zamyslov.centreit.testtask.exception.InvalidIndicatorValueException;
import zamyslov.centreit.testtask.entity.City;
import zamyslov.centreit.testtask.entity.User;
import zamyslov.centreit.testtask.entity.WeatherEntry;
import zamyslov.centreit.testtask.entity.WeatherIndicator;
import zamyslov.centreit.testtask.repository.WeatherEntryRepository;

import java.time.LocalDate;

@Service
public class WeatherService {

    private final WeatherEntryRepository weatherEntryRepository;

    public WeatherService(WeatherEntryRepository weatherEntryRepository) {
        this.weatherEntryRepository = weatherEntryRepository;
    }

    public Iterable<WeatherEntry> getWeatherByCityAndDate(City city, LocalDate date) throws InternalNullPointerException {
        if (city == null || date == null)
            throw new InternalNullPointerException("Null argument encountered in getWeatherByCityAndDate of WeatherService");
        return weatherEntryRepository.findAllByCityAndDate(city, date);
    }

    public Long addWeather(User user, City city, LocalDate date, WeatherIndicator indicator, Long indicatorValue)
            throws InternalNullPointerException, InvalidIndicatorValueException
    {
        if (user == null || city == null || date == null || indicator == null || indicatorValue == null)
            throw new InternalNullPointerException("Null argument encountered in addWeather of WeatherService");
        if (!indicator.isValidValue(indicatorValue))
            throw new InvalidIndicatorValueException("Value " + indicatorValue + "is unacceptable for "
                    + indicator.getName() + " indicator");
        WeatherEntry weatherEntry = weatherEntryRepository.findByCityAndDateAndIndicator(city, date, indicator)
                .orElse(new WeatherEntry());
        weatherEntry.setUser(user);
        weatherEntry.setCity(city);
        weatherEntry.setDate(date);
        weatherEntry.setIndicator(indicator);
        weatherEntry.setIndicatorValue(indicatorValue);
        weatherEntryRepository.save(weatherEntry);
        return weatherEntry.getId();
    }


}
