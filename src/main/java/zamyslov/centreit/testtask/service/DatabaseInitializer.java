package zamyslov.centreit.testtask.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zamyslov.centreit.testtask.entity.*;
import zamyslov.centreit.testtask.repository.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

@Service
public class DatabaseInitializer {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CityRepository cityRepository;
    private WeatherIndicatorRepository indicatorRepository;
    private WeatherEntryRepository weatherEntryRepository;

    private Role admin, user;
    private User baron, graf, herzog;
    private City moscow, ekaterinburg;
    private WeatherIndicator[] indicators;

    public DatabaseInitializer(RoleRepository roleRepository, UserRepository userRepository,
                               CityRepository cityRepository, WeatherIndicatorRepository indicatorRepository,
                               WeatherEntryRepository weatherEntryRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.indicatorRepository = indicatorRepository;
        this.weatherEntryRepository = weatherEntryRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void populateDatabase() {
        clearTables();
        createRoles();
        createUsers();
        createCities();
        createIndicators();
        createWeatherEntries();
    }

    private void clearTables() {
    }

    private void createRoles() {
        admin = new Role();
        admin.setName("ADMIN");
        admin.setPermissions(Permissions.READ | Permissions.WRITE);
        user = new Role();
        user.setName("USER");
        user.setPermissions(Permissions.READ);
        roleRepository.save(admin);
        roleRepository.save(user);
    }

    private void createUsers() {
        baron = new User();
        baron.setName("baron");
        baron.setPassword(passwordEncoder.encode("baron74"));
        baron.setRole(user);
        graf = new User();
        graf.setName("graf");
        graf.setPassword(passwordEncoder.encode("graf74"));
        graf.setRole(admin);
        herzog = new User();
        herzog.setName("herzog");
        herzog.setPassword(passwordEncoder.encode("herzog74"));
        herzog.setRole(admin);
        userRepository.save(baron);
        userRepository.save(graf);
        userRepository.save(herzog);
    }

    private void createCities() {
        moscow = new City();
        moscow.setName("Москва");
        ekaterinburg = new City();
        ekaterinburg.setName("Екатеринбург");
        cityRepository.save(moscow);
        cityRepository.save(ekaterinburg);
    }

    private void createIndicators() {
        indicators = new WeatherIndicator[4];
        indicators[0] = new WeatherIndicator();
        indicators[0].setName("Относительная влажность воздуха");
        indicators[0].setMeasurementUnitName("%");
        indicators[0].setMinValue(0L);
        indicators[0].setMaxValue(100L);
        indicators[1] = new WeatherIndicator();
        indicators[1].setName("Температура воздуха");
        indicators[1].setMeasurementUnitName("˚С");
        indicators[1].setMinValue(-80L);
        indicators[1].setMaxValue(80L);
        indicators[2] = new WeatherIndicator();
        indicators[2].setName("Скорость ветра");
        indicators[2].setMeasurementUnitName("м/с");
        indicators[2].setMinValue(0L);
        indicators[2].setMaxValue(50L);
        indicators[3] = new WeatherIndicator();
        indicators[3].setName("Направление ветра");
        indicators[3].setMeasurementUnitName(null);
        indicators[3].setMinValue(0L);
        indicators[3].setMaxValue(8L);
        indicatorRepository.save(indicators[0]);
        indicatorRepository.save(indicators[1]);
        indicatorRepository.save(indicators[2]);
        indicatorRepository.save(indicators[3]);
    }

    private void createWeatherEntries() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            WeatherEntry entry = new WeatherEntry();
            entry.setUser(random.nextBoolean() ? graf : herzog);
            entry.setCity(random.nextBoolean() ? moscow : ekaterinburg);
            entry.setDate(LocalDate.of(2018, Month.JANUARY, random.nextBoolean() ? 1 : 2));
            WeatherIndicator randomIndicator = indicators[random.nextInt(indicators.length)];
            entry.setIndicator(randomIndicator);
            long randomValue = random.nextInt(100);
            if (randomIndicator.hasMaxValue())
                randomValue %= randomIndicator.getMaxValue() - (randomIndicator.hasMinValue() ? randomIndicator.getMinValue() : 0);
            randomValue += (randomIndicator.hasMinValue() ? randomIndicator.getMinValue() : 0);
            entry.setIndicatorValue(randomValue);
            if (weatherEntryRepository.findByCityAndDateAndIndicator(entry.getCity(), entry.getDate(), randomIndicator).isPresent())
                continue;
            weatherEntryRepository.save(entry);
            System.out.println(entry);
        }
    }
}
