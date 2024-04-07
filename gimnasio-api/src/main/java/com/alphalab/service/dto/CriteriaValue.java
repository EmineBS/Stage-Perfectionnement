package com.alphalab.service.dto;

public class CriteriaValue {

    private Long idCriteria;
    private Double value;

    public CriteriaValue() {}

    public CriteriaValue(Long idCriteria, Double value) {
        this.idCriteria = idCriteria;
        this.value = value;
    }

    public Long getIdCriteria() {
        return idCriteria;
    }

    public void setIdCriteria(Long idCriteria) {
        this.idCriteria = idCriteria;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CriteriaValue{" + "idCriteria=" + idCriteria + ", value=" + value + '}';
    }
}
