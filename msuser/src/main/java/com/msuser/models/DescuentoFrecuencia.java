package com.msuser.models;

public class DescuentoFrecuencia {
    private Long id;
    private String categoria;
    private Integer minVisitas;
    private Integer maxVisitas;
    private Double porcentajeDescuento;
    private Double TotalFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getMinVisitas() {
        return minVisitas;
    }

    public void setMinVisitas(Integer minVisitas) {
        this.minVisitas = minVisitas;
    }

    public Integer getMaxVisitas() {
        return maxVisitas;
    }

    public void setMaxVisitas(Integer maxVisitas) {
        this.maxVisitas = maxVisitas;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Double getTotalFinal() {
        return TotalFinal;
    }

    public void setTotalFinal(Double totalFinal) {
        TotalFinal = totalFinal;
    }
}
