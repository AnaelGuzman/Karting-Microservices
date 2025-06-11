package com.msfrecuencydiscount.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "descuentos_frecuentes")
public class DescuentoFrecuente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private Integer minVisitas;
    private Integer maxVisitas;
    private Double porcentajeDescuento;
    private Double TotalFinal;

    // Constructores
    public DescuentoFrecuente() {
    }

    public DescuentoFrecuente(String categoria, Integer minVisitas, Integer maxVisitas, Double porcentajeDescuento) {
        this.categoria = categoria;
        this.minVisitas = minVisitas;
        this.maxVisitas = maxVisitas;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    // Getters y Setters
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