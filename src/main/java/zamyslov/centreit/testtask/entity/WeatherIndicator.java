package zamyslov.centreit.testtask.entity;

import zamyslov.centreit.testtask.entity.validation.MinMaxConstraint;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/***
 * Описание сущности Погодный показатель
 */
@Entity
@MinMaxConstraint
public class WeatherIndicator implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    private Long minValue;
    private Long maxValue;
    private String measurementUnitName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public boolean hasMinValue() {
        return minValue != null;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public boolean hasMaxValue() {
        return maxValue != null;
    }

    public String getMeasurementUnitName() {
        return measurementUnitName;
    }

    public void setMeasurementUnitName(String measurementUnitName) {
        this.measurementUnitName = measurementUnitName;
    }

    public boolean isValidValue(Long indicatorValue) {
        return (minValue == null || minValue <= indicatorValue) && (maxValue == null || maxValue >= indicatorValue);
    }
}
