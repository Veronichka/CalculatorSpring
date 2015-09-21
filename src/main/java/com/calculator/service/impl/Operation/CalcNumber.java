package com.calculator.service.impl.Operation;


public class CalcNumber implements CalcItem {
    private Double value;

    public CalcNumber(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
