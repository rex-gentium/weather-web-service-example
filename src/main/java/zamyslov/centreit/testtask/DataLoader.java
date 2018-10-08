package zamyslov.centreit.testtask;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zamyslov.centreit.testtask.entity.*;
import zamyslov.centreit.testtask.repository.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

/***
 * Наполняет базу данных тестовыми записями при запуске приложения.
 * Это черновой код, не стоит его оценивать, обычная БД должна наполняться
 * скриптом либо руками
 */
@Component
public class DataLoader implements ApplicationRunner {

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

    public static Long moscowId, ekaterinburgId, humidityId;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository,
                               CityRepository cityRepository, WeatherIndicatorRepository indicatorRepository,
                               WeatherEntryRepository weatherEntryRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.indicatorRepository = indicatorRepository;
        this.weatherEntryRepository = weatherEntryRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(ApplicationArguments args) {
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
        moscowId = moscow.getId();
        ekaterinburgId = ekaterinburg.getId();
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
        humidityId = indicators[0].getId();
    }

    private void createWeatherEntries() {
        // нам важны город, дата, индикатор и количество записей, остальное рандомно
        WeatherEntry[] entries = new WeatherEntry[6];
        entries[0] = new WeatherEntry();
        entries[0].setCity(moscow);
        entries[0].setDate(LocalDate.of(2018, Month.JANUARY, 2));
        entries[0].setIndicator(indicators[0]);
        entries[1] = new WeatherEntry();
        entries[1].setCity(moscow);
        entries[1].setDate(LocalDate.of(2018, Month.JANUARY, 3));
        entries[1].setIndicator(indicators[1]);
        entries[2] = new WeatherEntry();
        entries[2].setCity(moscow);
        entries[2].setDate(LocalDate.of(2018, Month.JANUARY, 3));
        entries[2].setIndicator(indicators[2]);
        entries[3] = new WeatherEntry();
        entries[3].setCity(ekaterinburg);
        entries[3].setDate(LocalDate.of(2018, Month.JANUARY, 1));
        entries[3].setIndicator(indicators[3]);
        entries[4] = new WeatherEntry();
        entries[4].setCity(ekaterinburg);
        entries[4].setDate(LocalDate.of(2018, Month.JANUARY, 1));
        entries[4].setIndicator(indicators[0]);
        entries[5] = new WeatherEntry();
        entries[5].setCity(ekaterinburg);
        entries[5].setDate(LocalDate.of(2018, Month.JANUARY, 2));
        entries[5].setIndicator(indicators[1]);
        Random random = new Random();
        for (WeatherEntry entry : entries) {
            entry.setUser(random.nextBoolean() ? graf : herzog);
            long randomValue = random.nextInt(100);
            WeatherIndicator indicator = entry.getIndicator();
            if (indicator.hasMaxValue())
                randomValue %= indicator.getMaxValue() - (indicator.hasMinValue() ? indicator.getMinValue() : 0);
            randomValue += (indicator.hasMinValue() ? indicator.getMinValue() : 0);
            entry.setIndicatorValue(randomValue);
            weatherEntryRepository.save(entry);
            System.out.println(entry);
        }
    }
}
