package zamyslov.centreit.testtask.json;

import zamyslov.centreit.testtask.entity.WeatherEntry;

import java.io.Serializable;

public class WeatherCondition implements Serializable {
    private String name;
    private Long value;
    private String measurementUnitName;

    public WeatherCondition() {}

    public WeatherCondition(String name, Long value, String measurementUnitName) {
        this.name = name;
        this.value = value;
        this.measurementUnitName = measurementUnitName;
    }

    public WeatherCondition(WeatherEntry weatherEntry) {
        this.name = weatherEntry.getIndicator().getName();
        this.value = weatherEntry.getIndicatorValue();
        this.measurementUnitName = weatherEntry.getIndicator().getMeasurementUnitName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getMeasurementUnitName() {
        return measurementUnitName;
    }

    public void setMeasurementUnitName(String measurementUnitName) {
        this.measurementUnitName = measurementUnitName;
    }
}
