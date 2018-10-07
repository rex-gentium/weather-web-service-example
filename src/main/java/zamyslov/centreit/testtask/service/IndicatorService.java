package zamyslov.centreit.testtask.service;

import org.springframework.stereotype.Service;
import zamyslov.centreit.testtask.exception.RecordNotFoundException;
import zamyslov.centreit.testtask.entity.WeatherIndicator;
import zamyslov.centreit.testtask.repository.WeatherIndicatorRepository;

@Service
public class IndicatorService {
    private final WeatherIndicatorRepository weatherIndicatorRepository;

    public IndicatorService(WeatherIndicatorRepository weatherIndicatorRepository) {
        this.weatherIndicatorRepository = weatherIndicatorRepository;
    }

    public WeatherIndicator getIndicatorById(Long id) throws RecordNotFoundException {
        return weatherIndicatorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Weather indicator with id " + id + " not found"));
    }
}
