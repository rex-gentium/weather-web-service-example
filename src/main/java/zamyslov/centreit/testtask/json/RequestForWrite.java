package zamyslov.centreit.testtask.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class RequestForWrite extends BasicRequest {
    private Long cityId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private Long weatherConditionId;
    private Long weatherConditionValue;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getWeatherConditionId() {
        return weatherConditionId;
    }

    public void setWeatherConditionId(Long weatherConditionId) {
        this.weatherConditionId = weatherConditionId;
    }

    public Long getWeatherConditionValue() {
        return weatherConditionValue;
    }

    public void setWeatherConditionValue(Long weatherConditionValue) {
        this.weatherConditionValue = weatherConditionValue;
    }
}
