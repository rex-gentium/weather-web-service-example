package zamyslov.centreit.testtask.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/***
 * Данные запроса на получение данных о погоде (описан в ТЗ)
 */
public class RequestForRead extends BasicRequest {
    private Long cityId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

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
}
