package com.alphalab.service.dto;

import java.time.Instant;

public class ProgressValue {

    private Instant when;
    private Double value;

    public ProgressValue() {}

    public ProgressValue(Instant when, Double value) {
        this.when = when;
        this.value = value;
    }

    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProgressValue{" + "when=" + when + ", value=" + value + '}';
    }
}
