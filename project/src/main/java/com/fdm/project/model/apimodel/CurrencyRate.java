package com.fdm.project.model.apimodel;

public class CurrencyRate {
    private String name;

    private String unit;

    private String type;

    private Double value;

    public CurrencyRate() {

    }

    public CurrencyRate(String name, String unit, String type, Double value) {
        this.name = name;
        this.unit = unit;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
