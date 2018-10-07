package zamyslov.centreit.testtask.json;

import zamyslov.centreit.testtask.entity.WeatherEntry;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ResponseForWeatherEntryRead extends BasicResponse {
    private String cityName;
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

    public void setWeatherConditionList(Iterable<WeatherEntry> weatherEntries) {
        this.weatherConditionList = new LinkedList<>();
        for (WeatherEntry weatherEntry : weatherEntries)
            weatherConditionList.add(new WeatherCondition(weatherEntry));
    }
}
