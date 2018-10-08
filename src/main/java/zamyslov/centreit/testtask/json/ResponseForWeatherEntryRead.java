package zamyslov.centreit.testtask.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import zamyslov.centreit.testtask.entity.WeatherEntry;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/***
 * Данные ответа на запрос получения данных о погоде в указанном
 * городе на указанную дату (описан в ТЗ)
 */
public class ResponseForWeatherEntryRead extends BasicResponse {
    private String cityName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private List<WeatherCondition> weatherConditionList;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<WeatherCondition> getWeatherConditionList() {
        return weatherConditionList;
    }

    public void setWeatherConditionList(List<WeatherCondition> weatherConditionList) {
        this.weatherConditionList = weatherConditionList;
    }

    public void setWeatherConditionListFromEntries(Iterable<WeatherEntry> weatherEntries) {
        this.weatherConditionList = new LinkedList<>();
        for (WeatherEntry weatherEntry : weatherEntries)
            weatherConditionList.add(new WeatherCondition(weatherEntry));
    }
}
