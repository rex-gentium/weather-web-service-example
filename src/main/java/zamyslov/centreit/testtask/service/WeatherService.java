package zamyslov.centreit.testtask.service;

import org.springframework.stereotype.Service;
import zamyslov.centreit.testtask.exception.*;
import zamyslov.centreit.testtask.entity.*;
import zamyslov.centreit.testtask.repository.WeatherEntryRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/***
 * Служба, отвечающая за взаимодействие с аднными о погодных записях
 */
@Service
public class WeatherService {

    private final WeatherEntryRepository weatherEntryRepository;

    public WeatherService(WeatherEntryRepository weatherEntryRepository) {
        this.weatherEntryRepository = weatherEntryRepository;
    }

    /***
     * Возвращает список записей о погоде по указанному городу и дате
     * @param city город
     * @param date дата; null трактуется как текущая дата на сервере
     * @return список записей (пустой, если по такому городу нет записей в эту дату)
     * @throws InternalNullPointerException при передаче null-параметра
     */
    public Iterable<WeatherEntry> getWeatherByCityAndDate(@NotNull City city, LocalDate date) throws InternalNullPointerException {
        if (city == null)
            throw new InternalNullPointerException("Null argument encountered in getWeatherByCityAndDate of WeatherService");
        if (date == null)
            date = LocalDate.now();
        return weatherEntryRepository.findAllByCityAndDate(city, date);
    }

    /***
     * Добавляет запись о погоде в базу. Если запись с такими атрибутами (город, дата, показатель) уже имеется,
     * изменяет уже существующую запись от имени пользователя
     * @param user пользователь, вносящий запись
     * @param city город
     * @param date дата; null трактуется как текущая дата на сервере
     * @param indicator погодный показатель
     * @param indicatorValue значение показателя
     * @return id внесенной (или измененной) записи
     * @throws InternalNullPointerException при передаче null-параметра
     * @throws InvalidIndicatorValueException если значение погодного показателя выходит за допустимые пределы
     */
    public Long addWeather(@NotNull User user, @NotNull City city, LocalDate date,
                           @NotNull WeatherIndicator indicator, @NotNull Long indicatorValue)
            throws InternalNullPointerException, InvalidIndicatorValueException
    {
        if (user == null || city == null || indicator == null || indicatorValue == null)
            throw new InternalNullPointerException("Null argument encountered in addWeather of WeatherService");
        if (date == null)
            date = LocalDate.now();
        if (!indicator.isValidValue(indicatorValue))
            throw new InvalidIndicatorValueException("Value " + indicatorValue + " is unacceptable for "
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
