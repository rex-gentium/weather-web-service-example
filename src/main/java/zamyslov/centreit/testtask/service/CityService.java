package zamyslov.centreit.testtask.service;

import org.springframework.stereotype.Service;
import zamyslov.centreit.testtask.exception.RecordNotFoundException;
import zamyslov.centreit.testtask.entity.City;
import zamyslov.centreit.testtask.repository.CityRepository;

import java.util.Optional;

/***
 * Служба, отвечающая за взаимодействие с данными о городах
 */
@Service
public class CityService {
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /***
     * Возвращает город из БД по id
     * @param id города
     * @return город
     * @throws RecordNotFoundException если город с таким id не найден в базе
     */
    public City getCityById(Long id) throws RecordNotFoundException {
        Optional<City> city = cityRepository.findById(id);
        return city.orElseThrow(() -> new RecordNotFoundException("City with id " + id + " not found"));
    }
}
