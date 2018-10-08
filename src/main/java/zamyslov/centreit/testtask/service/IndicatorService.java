package zamyslov.centreit.testtask.service;

import org.springframework.stereotype.Service;
import zamyslov.centreit.testtask.exception.RecordNotFoundException;
import zamyslov.centreit.testtask.entity.WeatherIndicator;
import zamyslov.centreit.testtask.repository.WeatherIndicatorRepository;

/***
 * Служба, отвечающая за взаимодействие с данными о погодных показателях
 */
@Service
public class IndicatorService {
    private final WeatherIndicatorRepository weatherIndicatorRepository;

    public IndicatorService(WeatherIndicatorRepository weatherIndicatorRepository) {
        this.weatherIndicatorRepository = weatherIndicatorRepository;
    }

    /***
     * Возвращает погодный показатель из БД по id
     * @param id показателя
     * @return погодный показатель
     * @throws RecordNotFoundException если показатель с таким id не найден в БД
     */
    public WeatherIndicator getIndicatorById(Long id) throws RecordNotFoundException {
        return weatherIndicatorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Weather indicator with id " + id + " not found"));
    }
}
