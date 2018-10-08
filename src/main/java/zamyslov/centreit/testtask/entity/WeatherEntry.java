package zamyslov.centreit.testtask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import zamyslov.centreit.testtask.entity.validation.WritePermissionConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/***
 * Описание сущности Запись о погоде
 */
@Entity
// в данной предметной области (нелогично видеть два разных значения одного показателя на одну дату)
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"city_id", "date", "indicator_id"}))
@WritePermissionConstraint
public class WeatherEntry implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private City city;
    @NotNull
    //@Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    @ManyToOne
    private WeatherIndicator indicator;
    @NotNull
    private Long indicatorValue;
    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public WeatherIndicator getIndicator() {
        return indicator;
    }

    public void setIndicator(WeatherIndicator indicator) {
        this.indicator = indicator;
    }

    public Long getIndicatorValue() {
        return indicatorValue;
    }

    public void setIndicatorValue(Long indicatorValue) {
        this.indicatorValue = indicatorValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "WeatherEntry{" +
                "id=" + id +
                ", city=" + city.getName() + "(id=" + city.getId() + ")" +
                ", date=" + date.toString() +
                ", indicator=" + indicator.getName() + "(id=" + indicator.getId() + ")" +
                ", indicatorValue=" + indicatorValue + " " + indicator.getMeasurementUnitName() +
                ", user=" + user.getName() + "(id=" + user.getId() + " " +
                '}';
    }
}
