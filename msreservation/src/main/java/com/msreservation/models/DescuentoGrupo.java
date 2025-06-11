package com.msreservation.models;

public class DescuentoGrupo {
    private Long id;
    private Integer minPersonas;
    private Integer maxPersonas;
    private Double porcentajeDescuento;
    private Double TotalFinal;

    public DescuentoGrupo() {
    }
    public DescuentoGrupo(Long id, Integer minPersonas, Integer maxPersonas, Double porcentajeDescuento, Double TotalFinal) {
        this.id = id;
        this.minPersonas = minPersonas;
        this.maxPersonas = maxPersonas;
        this.porcentajeDescuento = porcentajeDescuento;
        this.TotalFinal = TotalFinal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinPersonas() {
        return minPersonas;
    }

    public void setMinPersonas(Integer minPersonas) {
        this.minPersonas = minPersonas;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Integer getMaxPersonas() {
        return maxPersonas;
    }

    public void setMaxPersonas(Integer maxPersonas) {
        this.maxPersonas = maxPersonas;
    }

    public Double getTotalFinal() {
        return TotalFinal;
    }

    public void setTotalFinal(Double totalFinal) {
        TotalFinal = totalFinal;
    }
}
