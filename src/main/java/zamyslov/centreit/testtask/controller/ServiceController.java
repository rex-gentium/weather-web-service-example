package zamyslov.centreit.testtask.controller;

import org.springframework.web.bind.annotation.*;
import zamyslov.centreit.testtask.exception.*;
import zamyslov.centreit.testtask.json.*;
import zamyslov.centreit.testtask.entity.*;
import zamyslov.centreit.testtask.service.CityService;
import zamyslov.centreit.testtask.service.IndicatorService;
import zamyslov.centreit.testtask.service.UserService;
import zamyslov.centreit.testtask.service.WeatherService;

@RestController
@RequestMapping("/api")
public class ServiceController {

    private final WeatherService weatherService;
    private final UserService userService;
    private final CityService cityService;
    private final IndicatorService indicatorService;

    public ServiceController(WeatherService weatherService, UserService userService, CityService cityService,
                             IndicatorService indicatorService) {
        this.weatherService = weatherService;
        this.userService = userService;
        this.cityService = cityService;
        this.indicatorService = indicatorService;
    }

    @GetMapping("/")
    public String getHelloWorld() {
        return "Hello, World!";
    }

    @PostMapping("/get")
    public ResponseForWeatherEntryRead getWeatherData(@RequestBody RequestForRead request) {
        ResponseForWeatherEntryRead response = new ResponseForWeatherEntryRead();
        try {
            User user = userService.getUserByCredentialsAndPermission(request.getUsername(), request.getPassword(), Permissions.READ);
            City city = cityService.getCityById(request.getCityId());
            Iterable<WeatherEntry> weatherEntries = weatherService.getWeatherByCityAndDate(city, request.getDate());
            response.setCityName(city.getName());
            response.setDate(request.getDate());
            response.setWeatherConditionList(weatherEntries);
            response.setResultState(BasicResponse.ResultState.HANDLED);
        } catch (RecordNotFoundException | PasswordMismatchException | AccessRightsException e) {
            response.setResultState(BasicResponse.ResultState.ERROR);
            response.setResultMessage(e.getMessage());
        } catch (InternalNullPointerException e) {
            e.printStackTrace();
            response.setResultState(BasicResponse.ResultState.ERROR);
            response.setResultMessage("Internal server error occurred");
        }
        return response;
    }

    @PostMapping("/put")
    public BasicResponse putWeatherData(@RequestBody RequestForWrite request) {
        BasicResponse response = new BasicResponse();
        try {
            User user = userService.getUserByCredentialsAndPermission(request.getUsername(), request.getPassword(), Permissions.WRITE);
            City city = cityService.getCityById(request.getCityId());
            WeatherIndicator indicator = indicatorService.getIndicatorById(request.getWeatherConditionId());
            weatherService.addWeather(user, city, request.getDate(), indicator, request.getWeatherConditionValue());
            response.setResultState(BasicResponse.ResultState.HANDLED);
        } catch (RecordNotFoundException | AccessRightsException | PasswordMismatchException | InvalidIndicatorValueException e) {
            response.setResultState(BasicResponse.ResultState.ERROR);
            response.setResultMessage(e.getMessage());
        } catch (InternalNullPointerException e) {
            e.printStackTrace();
            response.setResultState(BasicResponse.ResultState.ERROR);
            response.setResultMessage("Internal server error occurred");
        }
        return response;
    }
}
